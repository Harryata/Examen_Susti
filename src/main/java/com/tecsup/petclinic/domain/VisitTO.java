package com.tecsup.petclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Objecto de Transferencia para Visit
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VisitTO {

    private Integer id;
    private Integer petId;
    private String visitDate;
    private String description;

}
