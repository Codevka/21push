package buaa.backend.response;

import buaa.backend.metadata.RentType;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class DownLoad {
    private static final Logger logger = LoggerFactory.getLogger(DownLoad.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @RequestMapping(value = "/download")
    public void response(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            OutputStream out = response.getOutputStream();
            String fileName = id + ".pdf";
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            response.setContentType("application/octet-stream");
            //输出并关闭
            getPdf(out, getInStream(id));
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private void getPdf(OutputStream out, ByteArrayInputStream in) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();
        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        worker.parseXHtml(writer, document, in, StandardCharsets.UTF_8, new AsianFontProvider());
        document.close();
        writer.close();
        out.flush();
        out.close();
    }

    private ByteArrayInputStream getInStream(String contractId) throws IOException {
        Map<String, String> info = getInfo(contractId);
//        info.put("temp", "classpath:templates/ShortContract.html");
//        info.put("{{name}}", "12321");
//        info.put("{{city}}", "1243423");
//        info.put("{{area}}", "12342342");
//        info.put("{{durationTime}}", "dfgfsg");
//        info.put("{{price}}", "fdgsd");
//        info.put("{{totalPrice}}", "fdgsd");
//        info.put("{{startDate}}", "fdgsd");
//        info.put("{{endDate}}", "fdgsd");
        String data = "";
        ClassPathResource cpr = new ClassPathResource(info.get("temp"));
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.warn("IOException");
            e.printStackTrace();
        }
        data = data.replace("{{name}}", info.get("{{name}}"));
        data = data.replace("{{city}}", info.get("{{city}}"));
        data = data.replace("{{province}}", info.get("{{province}}"));
        data = data.replace("{{address}}", info.get("{{address}}"));
        data = data.replace("{{area}}", info.get("{{area}}"));
        data = data.replace("{{durationTime}}", info.get("{{durationTime}}"));
        data = data.replace("{{price}}", info.get("{{price}}"));
        data = data.replace("{{totalPrice}}", info.get("{{totalPrice}}"));
        data = data.replace("{{startDate}}", info.get("{{startDate}}"));
        data = data.replace("{{endDate}}", info.get("{{endDate}}"));

//        logger.trace(data);
        return new ByteArrayInputStream(data.getBytes());
    }

    private Map<String, String> getInfo(String contractId) {
        Map<String, Object> info = new HashMap<>();
        info.putAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from Orders where contractId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, Integer.parseInt(contractId));
            return cs;
        }, this::getOrderResult)));
        Integer username = (Integer) info.get("username");
        info.putAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from Account where username = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, username);
            return cs;
        }, this::getUserResult)));
        Integer houseId = (Integer) info.get("houseId");
        info.putAll(Objects.requireNonNull(jdbcTemplate.execute(con -> {
            String storedProc = "select * from House where houseId = ?";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(1, houseId);
            return cs;
        }, this::getHouseResult)));
        Map<String, String> res = new HashMap<>();
        if ((Integer) info.get("rentType") == RentType.LONG.ordinal()) {
            res.put("temp", "templates/LongContract.html");
        } else {
            res.put("temp", "templates/ShortContract.html");
        }
        res.put("{{name}}", (String) info.get("name"));
        res.put("{{city}}", (String) info.get("city"));
        res.put("{{area}}", (String) info.get("area"));
        res.put("{{province}}", (String) info.get("province"));
        res.put("{{address}}", (String) info.get("address"));
        res.put("{{durationTime}}", String.valueOf(info.get("contractDuration")));
        res.put("{{price}}", String.valueOf(info.get("price")));
        Integer price = (Integer) info.get("price");
        Integer lenth = (Integer) info.get("contractDuration");
        res.put("{{totalPrice}}", String.valueOf(price * lenth));
        Calendar c = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime((Date) info.get("rentTime"));
        if ((Integer) info.get("rentType") == RentType.LONG.ordinal()) {
            c.add(Calendar.MONTH, (Integer) info.get("contractDuration"));
        } else {
            c.add(Calendar.DATE, (Integer) info.get("contractDuration"));
        }
        res.put("{{startDate}}", info.get("rentTime").toString());
        res.put("{{endDate}}", s.format(c.getTime()));
        return res;
    }

    private Map<String, Object> getOrderResult(CallableStatement cs) throws SQLException {
        cs.execute();
        ResultSet rs = cs.getResultSet();
        Map<String, Object> res = new HashMap<>();
        while (rs.next()) {
            res.put("contractId", rs.getInt("contractId"));
            res.put("houseId", rs.getInt("houseId"));
            res.put("rentTime", rs.getDate("rentTime"));
            res.put("contractDuration", rs.getInt("contractDuration"));
            res.put("price", rs.getInt("price"));
            res.put("username", rs.getInt("username"));
        }
        return res;
    }

    private Map<String, Object> getUserResult(CallableStatement cs) throws SQLException {
        cs.execute();
        ResultSet rs = cs.getResultSet();
        Map<String, Object> res = new HashMap<>();
        while (rs.next()) {
            res.put("name", rs.getString("name"));
        }
        return res;
    }

    private Map<String, Object> getHouseResult(CallableStatement cs) throws SQLException {
        cs.execute();
        ResultSet rs = cs.getResultSet();
        Map<String, Object> res = new HashMap<>();
        while (rs.next()) {
            res.put("province", rs.getString("province"));
            res.put("city", rs.getString("city"));
            res.put("area", rs.getString("area"));
            res.put("address", rs.getString("address"));
            res.put("rentType", rs.getInt("rentType"));
        }
        return res;
    }
}

class AsianFontProvider extends XMLWorkerFontProvider {
    @Override
    public Font getFont(final String fontname, String encoding, float size, final int style) {
        try {
            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            return new Font(bfChinese, size, style);
        } catch (Exception ignore) {
        }
        return super.getFont(fontname, encoding, size, style);
    }
}
