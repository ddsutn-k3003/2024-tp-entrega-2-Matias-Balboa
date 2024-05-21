package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.Vianda;

import java.time.LocalDateTime;

public class ViandaMapper {

    public ViandaDTO map(Vianda vianda){
    //Seteo el DTO de vianda
        ViandaDTO viandaDTO = new ViandaDTO(vianda.getQr(),
                                            vianda.getFechaElavoracion(),
                                            vianda.getEstado(),
                                            vianda.getColaboradorId(),
                                            vianda.getHeladeraId()
                                );
        viandaDTO.setId(vianda.getId());

        return viandaDTO;
    }
}
