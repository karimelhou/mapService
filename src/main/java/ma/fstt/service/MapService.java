package ma.fstt.service;

import ma.fstt.dto.LogisticDTO;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import jakarta.transaction.Transactional;
import ma.fstt.common.exceptions.RecordNotFoundException;
import ma.fstt.common.messages.BaseResponse;
import ma.fstt.common.messages.CustomMessage;
import ma.fstt.common.utils.Topic;
import ma.fstt.dto.MapDTO;
import ma.fstt.entity.MapEntity;
import ma.fstt.repo.MapRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MapService {

    @Autowired
    private MapRepo mapRepo;


    private final WebClient webClient;

    public MapService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/api/v1/auth/users").build();
    }

    public List<MapDTO> findMapList() {
        return mapRepo.findAll().stream().map(this::copyMapEntityToDto).collect(Collectors.toList());
    }

    public List<MapDTO> find(Long id) {
        return mapRepo.findByLogisticId(id).stream().map(this::copyMapEntityToDto).collect(Collectors.toList());
    }

    public MapDTO findByMapId(Long mapId) {
        MapEntity userEntity = mapRepo.findById(mapId)
                .orElseThrow(() -> new RecordNotFoundException("Map id '" + mapId + "' does not exist !"));
        return copyMapEntityToDto(userEntity);
    }

    public BaseResponse createOrUpdateMap(MapDTO mapDTO) {
        // Vérifie d'abord l'existence de l'utilisateur avant de créer une map
        Mono<LogisticDTO> userMono = webClient.get()
                .uri("/{id}", mapDTO.getLogisticId())
                .retrieve()
                .bodyToMono(LogisticDTO.class);

        // Attendez la réponse du service d'authentification
        LogisticDTO userResponse = userMono.block();

        if (userResponse != null && userResponse.getLogisticId() != null) {
            // L'utilisateur existe, continuez avec la création ou la mise à jour de l'map
            MapEntity mapEntity = copyMapDtoToEntity(mapDTO);
            mapRepo.save(mapEntity);
            return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
        } else {
            // L'utilisateur n'existe pas, vous pouvez gérer cela en lançant une exception, par exemple
            throw new RecordNotFoundException("L'utilisateur avec l'ID " + mapDTO.getLogisticId() + " n'existe pas.");
        }

    }

    public BaseResponse updateMap(Long mapId, MapDTO updatedMapDTO) {
        // Check if the map with the given ID exists in the database
        if (!mapRepo.existsById(mapId)) {
            throw new RecordNotFoundException("Map id '" + mapId + "' does not exist!");
        }

        // Find the existing MapEntity by ID
        MapEntity existingMapEntity = mapRepo.findById(mapId)
                .orElseThrow(() -> new RecordNotFoundException("Map id '" + mapId + "' does not exist !"));

        // Update the fields of the existing entity with the values from the updated DTO
        existingMapEntity.setDetails(updatedMapDTO.getDetails());
        existingMapEntity.setLocation(updatedMapDTO.getLocation());



        // Save the updated entity back to the database
        mapRepo.save(existingMapEntity);
        return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());
    }

    public BaseResponse deleteMap(Long mapId) {
        if (mapRepo.existsById(mapId)) {
            mapRepo.deleteById(mapId);
        } else {
            throw new RecordNotFoundException("No record found for given id: " + mapId);
        }
        return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
    }


    private MapDTO copyMapEntityToDto(MapEntity mapEntity) {
        MapDTO mapDTO = new MapDTO();
        mapDTO.setMapId(mapEntity.getMapId());
        mapDTO.setLocation(mapEntity.getLocation());
        mapDTO.setDetails(mapEntity.getDetails());
        mapDTO.setLogisticId(mapEntity.getLogisticId());
        return mapDTO;
    }

    private MapEntity copyMapDtoToEntity(MapDTO mapDTO) {
        MapEntity userEntity = new MapEntity();
        userEntity.setMapId(mapDTO.getMapId());
        userEntity.setLocation(mapDTO.getLocation());
        userEntity.setDetails(mapDTO.getDetails());
        userEntity.setLogisticId(mapDTO.getLogisticId());
        return userEntity;
    }




}
