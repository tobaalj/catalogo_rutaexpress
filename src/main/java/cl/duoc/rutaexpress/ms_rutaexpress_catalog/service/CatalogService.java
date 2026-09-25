package cl.duoc.rutaexpress.ms_rutaexpress_catalog.service;

import cl.duoc.rutaexpress.ms_rutaexpress_catalog.dto.CatalogItemDTO;
import cl.duoc.rutaexpress.ms_rutaexpress_catalog.dto.CreateCatalogItemDTO;
import cl.duoc.rutaexpress.ms_rutaexpress_catalog.entity.CatalogItem;
import cl.duoc.rutaexpress.ms_rutaexpress_catalog.repository.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final CatalogRepository repository;

    public CatalogService(CatalogRepository repository) {
        this.repository = repository;
    }

    public List<CatalogItemDTO> getAllActiveItems() {
        return repository.findByActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CatalogItemDTO getItemById(Long id) {
        CatalogItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
        return convertToDTO(item);
    }

    public CatalogItemDTO createItem(CreateCatalogItemDTO dto) {
        CatalogItem item = new CatalogItem();
        item.setServiceName(dto.getServiceName());
        item.setDescription(dto.getDescription());
        item.setBasePrice(dto.getBasePrice());
        item.setAvailableCapacity(dto.getAvailableCapacity());
        item.setActive(true);

        CatalogItem saved = repository.save(item);
        return convertToDTO(saved);
    }

    private CatalogItemDTO convertToDTO(CatalogItem item) {
        CatalogItemDTO dto = new CatalogItemDTO();
        dto.setId(item.getId());
        dto.setServiceName(item.getServiceName());
        dto.setDescription(item.getDescription());
        dto.setBasePrice(item.getBasePrice());
        dto.setAvailableCapacity(item.getAvailableCapacity());
        dto.setActive(item.getActive());
        return dto;
    }
}