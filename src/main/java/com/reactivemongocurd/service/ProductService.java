package com.reactivemongocurd.service;


import com.reactivemongocurd.dto.ProductDto;
import com.reactivemongocurd.repository.ProductRepo;
import com.reactivemongocurd.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Flux<ProductDto> getProducts(){
        return productRepo.findAll()
                .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> getProduct(String id){
        return productRepo.findById(id)
                .map(AppUtils::entityToDto);
    }

    public Flux<ProductDto> getProductInRange(double min, double max){
        return productRepo.findByPriceBetween(Range.closed(min,max));
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(productRepo::insert)
                .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto>updateProduct(Mono<ProductDto> productDtoMono , String id){
        return productRepo.findById(id)
                .flatMap(p->productDtoMono.map(AppUtils::dtoToEntity))
                .doOnNext(e->e.setId(id))
                .flatMap(productRepo::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteProduct(String id){
        return productRepo.deleteById(id);
    }





}
