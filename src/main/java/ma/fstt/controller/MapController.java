package ma.fstt.controller;

import ma.fstt.common.messages.BaseResponse;
import ma.fstt.dto.MapDTO;
import ma.fstt.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RestController
@RequestMapping("/maps")

public class MapController {

    @Autowired
    private  MapService mapService;


    @GetMapping
    public ResponseEntity<List<MapDTO>> getAllMap() {
        List<MapDTO> list = mapService.findMapList();
        return new ResponseEntity<List<MapDTO>>(list, HttpStatus.OK);
    }

    @PostMapping(value = { "/add" })
    public ResponseEntity<BaseResponse> createMap(@RequestBody MapDTO userDTO) {
        BaseResponse response = mapService.createOrUpdateMap(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping(value = "/update/{id}")
    public ResponseEntity<BaseResponse> updateMap(
            @PathVariable("id") Long id,
            @RequestBody MapDTO updatedMapDTO) {

        BaseResponse response = mapService.updateMap(id, updatedMapDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BaseResponse> deleteMapById(@PathVariable Long id) {
        BaseResponse response = mapService.deleteMap(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MapDTO> getMapById(@PathVariable Long id) {
        MapDTO list = mapService.findByMapId(id);
        return new ResponseEntity<MapDTO>(list, HttpStatus.OK);
    }

}
