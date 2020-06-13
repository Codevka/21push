package buaa.backend;

import buaa.backend.service.HouseRentCount;
import buaa.backend.service.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/")
public class Test {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Mail mail;

    @RequestMapping("/hello")
    public List<?> hello() {
        List<?> res = jdbcTemplate.queryForList("select * from Account");
        System.out.println(res);
        mail.sendSimpleTextMail("holmium_jwh@qq.com", "hello", "hellooooo");
        ResultSet result = jdbcTemplate.execute((CallableStatementCreator) con -> {
            String storedProc = "{call allGetPasswordAndTypeById(?,?)}";
            CallableStatement cs = con.prepareCall(storedProc);
            cs.setInt(2, 1000000);
            cs.registerOutParameter(1, Types.INTEGER);
            return cs;
        }, cs -> {
            cs.execute();
            System.out.println(cs.getInt(1));
            ResultSet set = cs.getResultSet();
            System.out.println(2);
            System.out.println(set.getMetaData().getColumnCount());
            while (set.next()) {
                System.out.println(set.getString("password"));
            }
            return cs.getResultSet();
        });
        return res;
    }

    @RequestMapping("/fff")
    public int tt() {
        return HouseRentCount.count(1);
    }

    @RequestMapping("/ttt")
    public String temp() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:templates/LongContract.html");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "123456";
    }

    @RequestMapping("/id")
    public String res(@RequestParam String id) {
        return "ID is " + id;
    }

    @RequestMapping("/down")
    public void down(HttpServletRequest request, HttpServletResponse response) {
        try {
            OutputStream out = response.getOutputStream();
            String fileName = "stwen.txt";
            response.setHeader("Pragma", "No-cache"); // 设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            response.setContentType("application/octet-stream");
            //输出并关闭
            File file = ResourceUtils.getFile("classpath:templates/LongContract.html");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                out.write(scanner.nextLine().getBytes());
            }
//            byte[] content = file.getBytes();

            //如果从自己fast下载
            //String path = request.getParameter("path");
            //byte[] content = FDSTUtil.download(path);
            //文件名也可以从前端传递过来
            //String fileName = request.getParameter("fileName");

            //设置响应头信息
            // rep.setContentType("Image/" + fileName); // 设置相应类型,告诉浏览器输出的内容为图片

//            out.write(content);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
