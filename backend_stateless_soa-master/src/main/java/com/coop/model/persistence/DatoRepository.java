package com.coop.model.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop.model.Dato;

@Repository
public interface DatoRepository extends JpaRepository<Dato, Long> {
	public List<Dato> findByTopicoOrderByTiempoDesc(String topico);
	public List<Dato> findByTopicoAndTiempoGreaterThanEqualOrderByTiempoDesc(String topico, Date desde);
	public List<Dato> findByTopicoAndTiempoLessThanEqualOrderByTiempoDesc(String topico, Date hasta);
	public List<Dato> findByTopicoAndTiempoGreaterThanEqualAndTiempoLessThanEqualOrderByTiempoDesc(String topico, Date desde, Date hasta
			);
}
