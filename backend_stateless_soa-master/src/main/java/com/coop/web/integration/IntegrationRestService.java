package com.coop.web.integration;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coop.business.BusinessException;
import com.coop.business.IDatoBusiness;
import com.coop.business.NotFoundException;
import com.coop.model.Dato;
import com.coop.web.BaseRestService;
import com.coop.web.Constantes;

@RestController
@RequestMapping(Constantes.URL_INTEGRATION)
public class IntegrationRestService extends BaseRestService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IDatoBusiness datoBusiness;

	@PreAuthorize("hasRole('ROLE_INTEGRATION')")
	@GetMapping("/isauth")
	public ResponseEntity<String> isAuth() {
		return new ResponseEntity<String>(HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ROLE_INTEGRATION')")
	@GetMapping("/dato")
	public ResponseEntity<List<Dato>> list(@RequestParam(value = "topico") String topico,
			@RequestParam(value = "desde", required = false, defaultValue = "1970-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date desde,
			@RequestParam(value = "hasta", required = false, defaultValue = "1970-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date hasta) {
		boolean hayDesde = desde.getTime() != 10800000;
		boolean hayHasta = hasta.getTime() != 10800000;
		System.out.println(desde);
		System.out.println(hasta);

		try {
			if (hayDesde && hayHasta) {
				return new ResponseEntity<List<Dato>>(datoBusiness.listByTopicDesdeHasta(topico, desde, hasta),
						HttpStatus.OK);
			} else if (hayDesde && !hayHasta) {
				return new ResponseEntity<List<Dato>>(datoBusiness.listByTopicDesde(topico, desde), HttpStatus.OK);
			} else if (!hayDesde && hayHasta) {
				return new ResponseEntity<List<Dato>>(datoBusiness.listByTopicHasta(topico, hasta), HttpStatus.OK);
			} else {

				return new ResponseEntity<List<Dato>>(datoBusiness.listByTopic(topico), HttpStatus.OK);
			}
		} catch (BusinessException e) {
			return new ResponseEntity<List<Dato>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_INTEGRATION')")
	@GetMapping("/dato/{id}")
	public ResponseEntity<Dato> load(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<Dato>(datoBusiness.load(id), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Dato>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Dato>(HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ROLE_INTEGRATION')")
	@PostMapping("/dato")
	public ResponseEntity<Dato> add(@RequestBody Dato dato) {
		try {
			if (dato.getTiempo() == null)
				dato.setTiempo(new Date());
			datoBusiness.save(dato);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", Constantes.URL_INTEGRATION + "/dato/" + dato.getId());
			return new ResponseEntity<Dato>(responseHeaders, HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<Dato>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_INTEGRATION')")
	@PutMapping("/dato")
	public ResponseEntity<Dato> update(@RequestBody Dato dato) {
		try {
			datoBusiness.save(dato);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", Constantes.URL_INTEGRATION + "/dato/" + dato.getId());
			return new ResponseEntity<Dato>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Dato>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_INTEGRATION')")
	@DeleteMapping("/dato/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		try {
			datoBusiness.delete(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
