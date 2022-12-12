package com.javatechie.webflux.controller;

import com.javatechie.webflux.dto.Customer;
import com.javatechie.webflux.dto.Response;
import com.javatechie.webflux.service.CustomerService;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService service;


    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.loadAllCustomers();
    }



    //P your service
    @PostMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getAllCustomersStream() {
        return service.loadAllCustomersStream();
    }


    public void ifError(){
        //blah blah
    }

    //B his service
    @PostMapping(value = "/callerTest")
    public ResponseEntity<Response> callAPI() throws InterruptedException {
        HttpHeaders httpHeaders = new HttpHeaders();



        HttpEntity<String> requestEntity = new HttpEntity<>("setting", httpHeaders);
        WebClient webClient = WebClient.create("http://localhost:9191");
        Flux<Customer> dto=null;
        try {
            webClient.post().uri("/customers/stream")
                     .retrieve()
                     .bodyToFlux(Customer.class)

                     .subscribe(new Subscriber() {
                         Subscription s;
                         @Override
                         public void onSubscribe(Subscription s) {
                             s.request(1);
                             this.s = s;
                         }

                         @Override
                         public void onNext(Object o) {

                             Customer b = (Customer) o;
                             System.out.println("fazzzzzzzzzzzzzzzzzzzzzzith");
                             System.out.println(b.getName()+" from flux");
                             s.request(1);

                         }

                         @Override
                         public void onError(Throwable t) {
                             ifError();
                            System.out.println(t.toString());
                         }

                         @Override
                         public void onComplete() {
                             System.out.println("done");

                         }
                     });
        }catch (Exception ex){
            System.out.println("check");
        }
        //System.out.println("Result = " + dto.flatMap(res -> res.bodyToMono(String.class)));
        System.out.println("88888888888888888888888888888888888888888888888888888888888888888888");

//      ResponseEntity<ResponseDTO> dto = restTemplate.exchange("http://localhost:8088/policy-asia-adaptor/", HttpMethod.GET, requestEntity, ResponseDTO.class);
//        System.out.println(dto.blockLast().getMessage());

        return new ResponseEntity<>(new Response("OK","Success"), HttpStatus.OK);
    }
}
