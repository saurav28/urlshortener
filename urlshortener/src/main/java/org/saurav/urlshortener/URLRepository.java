package org.saurav.urlshortener;

import org.springframework.data.repository.CrudRepository;

public interface URLRepository extends CrudRepository<URL ,String>{
	
	

}
