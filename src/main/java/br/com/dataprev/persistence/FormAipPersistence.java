package br.com.dataprev.persistence;

import br.com.dataprev.entity.FormAipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormAipPersistence extends JpaRepository<FormAipEntity, Long> {
}
