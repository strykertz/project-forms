package br.com.dataprev.controller;

import br.com.dataprev.entity.EmailSenderEntity;
import br.com.dataprev.entity.FormAipEntity;
import br.com.dataprev.service.EmailSenderService;
import br.com.dataprev.service.FormAipService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class FormAipController {

    private final FormAipService formAipService;
    private final EmailSenderService emailSenderService;

    public FormAipController(FormAipService formAipService, EmailSenderService emailSenderService) {
        this.formAipService = formAipService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/getAip")
    public ModelAndView getAipById(@RequestParam Long id){

        ModelAndView mv = new ModelAndView("historico-aip");
        FormAipEntity formAipById = formAipService.getFormAipById(id);

        mv.addObject("getFormAip", formAipById);

        return mv;
    }

    @PostMapping("/delete")
    public ModelAndView deleteAipById(@RequestParam Long id) {

        formAipService.deleteFormAipById(id);
        return new ModelAndView("redirect:/buscar-todos-aip");
    }


    @PostMapping("/createNewAip")
    public ModelAndView createNewAip(FormAipEntity formAip) {

        ModelAndView mv = new ModelAndView("new-form-aip_2");

        mv.addObject("newFormAip", formAip);
        formAipService.insertFormAip(formAip);

        return new ModelAndView("redirect:/buscar-todos-aip");
    }

    @GetMapping("/new-form-aip")
    public ModelAndView getFormAip() {
        ModelAndView mv = new ModelAndView("new-form-aip_2");
        return mv.addObject("newFormAip", new FormAipEntity());

    }


    @GetMapping("/buscar-todos-aip")
    public ModelAndView getAllAip() {

        ModelAndView mv = new ModelAndView("saved-aip");

        List<FormAipEntity> allAip = formAipService.getAllAip();

        List<FormAipEntity> collect1 = allAip.stream().peek( c -> {
            if(c.isTrataDadoPessoal() || c.isDadoSensivel()) {
                c.setCriticidade("Muito Alto");
                c.setAnalise("Em Análise");
            } else {
                c.setCriticidade("Média");
                c.setAnalise("Aprovado");
            }
        }).toList();

        mv.addObject("formsAip", collect1);

        return mv;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateAipById(@PathVariable("id") Long id, FormAipEntity formAip) {
        ModelAndView mv = new ModelAndView("historico-aip");

        FormAipEntity formAipEntity = formAipService.updateFormAip(id, formAip);
        mv.addObject("getFormAip", formAipEntity);

        return mv;
    }

    @GetMapping("/home")
    public ModelAndView home(){

        ModelAndView mv = new ModelAndView("home");

        mv.addObject("newFormAip", new FormAipEntity());

        return mv;
    }

    @GetMapping("/historico")
    public ModelAndView historicoAip(){
        ModelAndView mv = new ModelAndView("historico-aip");
        mv.addObject("newFormAip", new FormAipEntity());
        return mv;
    }

    @GetMapping("/approveAip")
    public ModelAndView approveAip() {

        ModelAndView mv = new ModelAndView("approve-aip");

        List<FormAipEntity> allAip = formAipService.getAllAip();

        List<FormAipEntity> collect1 = allAip.stream().peek( c -> {
            if(c.isTrataDadoPessoal() || c.isDadoSensivel()) {
                c.setCriticidade("Muito Alto");
                c.setAnalise("Em Análise");
            } else {
                c.setCriticidade("Média");
                c.setAnalise("Aprovado");
            }
        }).toList();

        List<FormAipEntity> emAnalise = collect1.stream()
                .filter(i -> i.getAnalise().equals("Em Análise")).toList();

        mv.addObject("formsAip", emAnalise);

        return mv;
    }

    @PostMapping("/historicoApprove")
    public ModelAndView historicoAipApprove(@RequestParam Long id)  {
        ModelAndView mv = new ModelAndView("historico-aip-approve");
        FormAipEntity formAipById = formAipService.getFormAipById(id);
        mv.addObject("getFormAip", formAipById);

        return mv;
    }

    @GetMapping("/emailForm")
    public String showEmailForm(Model model) {
        model.addAttribute("emailSender", new EmailSenderEntity());
        return "emailForm";  // nome do template
    }

    @PostMapping("/sendEmail")
    public ModelAndView EnviarEmail(@ModelAttribute EmailSenderEntity emailSenderEntity ) {
        ModelAndView mv = new ModelAndView();

        emailSenderService.sendAipEmail(emailSenderEntity);
        mv.addObject("emailSender", emailSenderEntity);
        return new ModelAndView("redirect:/approveAip");
    }
}
