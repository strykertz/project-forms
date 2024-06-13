package br.com.dataprev.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailSenderEntity {

    private String approved;
    private String rejected;
    private String inconsistent;
}
