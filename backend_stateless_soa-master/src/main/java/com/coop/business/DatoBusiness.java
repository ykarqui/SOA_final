package com.coop.business;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coop.model.Dato;
import com.coop.model.persistence.DatoRepository;

@Service
public class DatoBusiness implements IDatoBusiness {

	@Autowired
	private DatoRepository datoDAO;

	@Override
	public Dato save(Dato dato) throws BusinessException {
		try {
			return datoDAO.save(dato);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Dato load(long id) throws BusinessException, NotFoundException {
		Optional<Dato> o;
		try {
			o = datoDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (o.isPresent())
			return o.get();
		else
			throw new NotFoundException();
	}

	@Override
	public void delete(long id) throws BusinessException {
		try {
			datoDAO.deleteById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public List<Dato> listByTopic(String topico) throws BusinessException {
		try {
			return datoDAO.findByTopicoOrderByTiempoDesc(topico);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Dato> listByTopicDesde(String topico, Date desde) throws BusinessException {
		try {
			return datoDAO.findByTopicoAndTiempoGreaterThanEqualOrderByTiempoDesc(topico, desde);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Dato> listByTopicHasta(String topico, Date hasta) throws BusinessException {
		try {
			return datoDAO.findByTopicoAndTiempoLessThanEqualOrderByTiempoDesc(topico, hasta);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Dato> listByTopicDesdeHasta(String topico, Date desde, Date hasta) throws BusinessException {
		try {
			return datoDAO.findByTopicoAndTiempoGreaterThanEqualAndTiempoLessThanEqualOrderByTiempoDesc(topico, desde,
					hasta);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
