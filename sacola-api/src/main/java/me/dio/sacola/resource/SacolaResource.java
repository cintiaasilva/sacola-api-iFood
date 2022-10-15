package me.dio.sacola.resource;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.resource.dto.ItemDto;
import me.dio.sacola.service.SacolaService;
import org.springframework.web.bind.annotation.*;

@Api(value="/ifood-devweek/sacolas")
@RestController
@RequestMapping("/ifood-devweek/sacolas")
@RequiredArgsConstructor  // cria o construtor do SacolaService
public class SacolaResource {

    private final SacolaService sacolaService;

    @PostMapping
    public Item incluirItem(@RequestBody ItemDto itemDto){
        return sacolaService.incluirItem(itemDto);
    }

    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable("id") Long id){
        return sacolaService.verSacola(id);
    }

    @PatchMapping("/fecharSacola/{id}")
    public Sacola fecharSacola(@PathVariable("id") Long id,
                               @RequestParam int formaPagamento){
        return sacolaService.fecharSacola(id, formaPagamento);
    }

}
