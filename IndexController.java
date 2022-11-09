package com.snykctf.serialsnyker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model,
                        HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        model.addAttribute("csrfToken", this.getCSRFToken());
        return "index";
    }

    @PostMapping("/")
    public String authenticate(Model model,
                               @RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String csrfToken) throws Exception {

        CSRFToken token = new CSRFToken();
        Object obj = null;
         try {
            obj = SerializationUtils.deserialize(csrfToken);
            System.out.println("TEST");
            System.out.println(obj);
            token = (CSRFToken) obj;
        model.addAttribute("exception", "helloworld!!");
        } catch (Exception ex) {
             if (obj == null) {
                 model.addAttribute("error", ex.getMessage());
             } else {
                 model.addAttribute("error", obj.toString() + ex.getMessage());
             }

             return "index";
        } catch (Error ex) {
             model.addAttribute("error",  ex.getMessage());
             return "index";
         }

        model.addAttribute("csrfToken", this.getCSRFToken());
        return "index";
    }

    private String getCSRFToken() {
        CSRFToken token = new CSRFToken();
        return SerializationUtils.serialize(token);
    }

}
