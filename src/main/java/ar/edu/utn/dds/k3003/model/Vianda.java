package ar.edu.utn.dds.k3003.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Vianda {

    private Long id;
    private String qr;
    private Long colaboradorId;
    private Integer heladeraId;
    private EstadoViandaEnum estado;
    private LocalDateTime fechaElavoracion;

    public Vianda(Long id,String qr,Long colaboradorId,Integer heladeraId,EstadoViandaEnum estado){
        this.id = id;
        this.qr = qr;
        this.colaboradorId = colaboradorId;
        this.heladeraId = heladeraId;
        this.estado = estado;
        this.fechaElavoracion = LocalDateTime.now();
    }


    public Boolean esValida(Integer mes, Integer anio){
        return (this.fechaElavoracion.getYear() == anio && this.fechaElavoracion.getMonthValue() == mes);
    }
}
