package org.backend.voyage.service;

import org.backend.voyage.dto.heber.HeberDto;
import org.backend.voyage.dto.heber.HeberResponse;
import org.backend.voyage.dto.heber.HeberUpDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IntHerberService {
    HeberResponse find_Heber(Long id);
    HeberResponse Create_Post(HeberDto p);
    HeberResponse  Update_Post(HeberUpDto p, Long id);
    HeberResponse  Delete_Post(Long id);
    Page<HeberResponse> list_Heber(Pageable p,String re);
    List<HeberResponse> list_heber_by_serch(String search);

}
