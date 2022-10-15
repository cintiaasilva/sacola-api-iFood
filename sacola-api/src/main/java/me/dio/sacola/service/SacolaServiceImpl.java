package me.dio.sacola.service;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.enumeration.FormaPagamento;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Restaurante;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.repository.ItemRepository;
import me.dio.sacola.repository.ProdutoRepository;
import me.dio.sacola.repository.SacolaRepository;
import me.dio.sacola.resource.dto.ItemDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SacolaServiceImpl implements SacolaService {

    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;

    @Override
    public Item incluirItem(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getSacolaId());

        if (sacola.isFechada()) {
            throw new RuntimeException("Esta sacola está fechada");
        }

        Item item = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Esse produto não existe");
                        }
                ))
                .build();

        List<Item> itensSacola = sacola.getItensSacola();

        if (itensSacola.isEmpty()){
            itensSacola.add(item);
        }else{
            Restaurante restauranteAtual = itensSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItem = item.getProduto().getRestaurante();

            if (restauranteAtual.equals(restauranteDoItem)){
                itensSacola.add(item);
            }else {
                throw new RuntimeException("Não é possivel adicionar produtos de restaurantes diferentes.\n" +
                        "Feche a sacola ou esvazie");
            }
        }

        List<Double> valorItens = new ArrayList<>();

        for (Item itemSacola : itensSacola){
            double valorTotalItens = itemSacola.getProduto().getValorUnitario() * itemSacola.getQuantidade();
            valorItens.add(valorTotalItens);
        }

        double valorTotalSacola = valorItens.stream()
                .mapToDouble(valorTotalItem -> valorTotalItem)
                .sum();

        sacola.setValorTotal(valorTotalSacola);

        sacolaRepository.save(sacola);

        return item;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Essa sacola não existe");
                }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int numFormaPagamento) {
        Sacola sacola = verSacola(id);

        if (sacola.getItensSacola().isEmpty()) {
            throw new RuntimeException("Inclua itens na sacola");
        }
        FormaPagamento formaPagamento = numFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);

        return sacolaRepository.save(sacola);
    }
}
