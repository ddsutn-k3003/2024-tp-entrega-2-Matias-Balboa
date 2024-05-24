package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.HeladeraDTO;
import ar.edu.utn.dds.k3003.facades.dtos.TemperaturaDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.Vianda;
import ar.edu.utn.dds.k3003.repositories.ViandaMapper;
import ar.edu.utn.dds.k3003.repositories.ViandaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Fachada implements ar.edu.utn.dds.k3003.facades.FachadaViandas {

    private final ViandaMapper viandaMapper;
    private final ViandaRepository viandaRepository;
    private FachadaHeladeras fachadaHeladeras;
    public Fachada(){
        this.viandaMapper = new ViandaMapper();
        this.viandaRepository = new ViandaRepository();
    }

    @Override
    public ViandaDTO agregar(ViandaDTO viandaDTO) {
        Vianda vianda = new Vianda(viandaDTO.getId(),viandaDTO.getCodigoQR(),viandaDTO.getColaboradorId(),viandaDTO.getHeladeraId(),viandaDTO.getEstado());
        vianda = this.viandaRepository.save(vianda);
        return viandaMapper.map(vianda);
    }

    @Override
    public ViandaDTO modificarEstado(String s, EstadoViandaEnum estadoViandaEnum) throws NoSuchElementException {

        //Obtengo la vianda que busco por QR
        Vianda vianda = this.viandaRepository.findByQr(s);
        //Cambio el estado de la vianda
        vianda.setEstado(estadoViandaEnum);
        //Guardo los cambios
        vianda = this.viandaRepository.save(vianda);

        return viandaMapper.map(vianda);
    }

    @Override
    public List<ViandaDTO> viandasDeColaborador(Long aLong, Integer integer, Integer integer1) throws NoSuchElementException {

        //Recibo la lista de Viandas
        List<Vianda> viandas = this.viandaRepository.findByColaborador(aLong);
        //Filtro por las fechas
        viandas = viandas.stream().filter(x -> x.esValida(integer,integer1)).toList();
        //Mapeo para obtener la lista de DTOS
        List<ViandaDTO> viandaDTOS = viandas.stream().map(x -> viandaMapper.map(x)).toList();

        return viandaDTOS;
    }

    @Override
    public ViandaDTO buscarXQR(String s) throws NoSuchElementException {
        //Obtengo la vianda que busco por QR
        Vianda vianda = this.viandaRepository.findByQr(s);

        return viandaMapper.map(vianda);
    }

    @Override
    public void setHeladerasProxy(FachadaHeladeras fachadaHeladeras) {
        this.fachadaHeladeras = fachadaHeladeras;
    }

    @Override
    public boolean evaluarVencimiento(String s) throws NoSuchElementException {

        //Busco la vianda por qr
        Vianda vianda = this.viandaRepository.findByQr(s);
        //listo las temperaturas
        List<TemperaturaDTO> temperaturaDTOS = fachadaHeladeras.obtenerTemperaturas(vianda.getHeladeraId());
        //Busco las temperaturas mayores a 5. Si la lista esta vacia es porque no esta vencida
         boolean result = ! temperaturaDTOS.stream().filter( x -> x.getTemperatura() >= 5).toList().isEmpty();
        return result;

    }

    @Override
    public ViandaDTO modificarHeladera(String s, int i) {

        //Obtengo la vianda que busco por QR
        Vianda vianda = this.viandaRepository.findByQr(s);
        //Cambio el estado de la vianda
        vianda.setHeladeraId(i);
        //Guardo los cambios
        vianda = this.viandaRepository.save(vianda);

        return viandaMapper.map(vianda);
    }
}
