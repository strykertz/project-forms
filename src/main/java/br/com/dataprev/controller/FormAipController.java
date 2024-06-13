package br.com.dataprev.controller;

import br.com.dataprev.entity.EmailSenderEntity;
import br.com.dataprev.entity.FormAipEntity;
import br.com.dataprev.service.EmailSenderService;
import br.com.dataprev.service.FormAipService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

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
    public String deleteAipById(@RequestParam Long id) {

        formAipService.deleteFormAipById(id);
        return "redirect:home";
    }


    @PostMapping("/createNewAip")
    public ModelAndView createNewAip(FormAipEntity formAip) {

        ModelAndView mv = new ModelAndView("new-form-aip");

        mv.addObject("newFormAip", formAip);
        formAipService.insertFormAip(formAip);

        return mv;
    }

    @GetMapping("/new-form-aip")
    public ModelAndView getFormAip() {
        ModelAndView mv = new ModelAndView("new-form-aip");
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

    @GetMapping("/approveAip/{id}")
    public ModelAndView approveAip(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("historico-aip");
        FormAipEntity formAipById = formAipService.getFormAipById(id);

        mv.addObject("getFormAip", formAipById);

        return mv;
    }

    @GetMapping("/enviar-email")
    public void testeEmail(EmailSenderEntity emailSenderEntity){
        emailSenderService.sendAipEmail(emailSenderEntity);
    }


}
