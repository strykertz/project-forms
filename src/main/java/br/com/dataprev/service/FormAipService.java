package br.com.dataprev.service;

import br.com.dataprev.entity.FormAipEntity;
import br.com.dataprev.persistence.FormAipPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormAipService {

    private final FormAipPersistence formAipPersistence;

    public FormAipService(FormAipPersistence formAipPersistence) {
        this.formAipPersistence = formAipPersistence;
    }

    public FormAipEntity getFormAipById(Long id) {
        return formAipPersistence.getReferenceById(id);
    }

    public void deleteFormAipById(Long id) {
        formAipPersistence.deleteById(id);
    }

    public void insertFormAip(FormAipEntity formAip) {
        String criticidade = "";
        if (formAip.isDadoSensivel() || formAip.isTrataDadoPessoal()) {
            criticidade = "Muito Alto";
        }
        formAip.setCriticidade(criticidade);

        formAipPersistence.save(formAip);
    }

    public List<FormAipEntity> getAllAip() {
        return formAipPersistence.findAll();
    }

   public FormAipEntity updateFormAip(Long id, FormAipEntity formAip) {

        FormAipEntity AipEntity = formAipPersistence.getReferenceById(id);

        AipEntity.setNomeProjeto(formAip.getNomeProjeto());
        AipEntity.setSigla(formAip.getSigla());
        AipEntity.setDadoSensivel(formAip.isDadoSensivel());
        AipEntity.setContextoTratamentoDadoPessoal(formAip.getContextoTratamentoDadoPessoal());
        AipEntity.setTratamentoLargaEscala(formAip.isTratamentoLargaEscala());
        AipEntity.setVolumeRegistroTitular(formAip.getVolumeRegistroTitular());
        AipEntity.setMonitoramentoVideo(formAip.isMonitoramentoVideo());
        AipEntity.setTrataDadoPessoal(formAip.isTrataDadoPessoal());

        return formAipPersistence.save(AipEntity);

    }

}
