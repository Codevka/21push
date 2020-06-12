package buaa.backend.response;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class DownLoad {
    //TODO /download?id=contractId get pdf
    @CrossOrigin
    @RequestMapping(value = "/download")
    public void response(@RequestParam String contractId, HttpServletRequest request, HttpServletResponse response) {
        try {
            OutputStream out = response.getOutputStream();
            String fileName = contractId + ".pdf";
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            response.setContentType("application/octet-stream");
            //输出并关闭

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private void getPdf(OutputStream out, ByteArrayInputStream in) throws DocumentException, IOException {

        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, out);
        // step 3
        document.open();
        // step 4
        //显示中文必须设置font-family。这里为宋体(simsun)。中文可为汉字，也可为汉字的unicode
        String str = "<div style='font-family:SimSun'>123\u6d4b\u8bd5abc测试</div>";

        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        worker.parseXHtml(writer, document, in, StandardCharsets.UTF_8);
        // step 5
        document.close();
        out.flush();
        out.close();
    }

    private ByteArrayInputStream getInStream(String contractId) throws IOException {
        File file = ResourceUtils.getFile("classpath:templates/LongContract.html");
        FileInputStream in = new FileInputStream(file);
    }

    private Map<String, Object> getInfo(String contractId) {
        return null
    }
}
