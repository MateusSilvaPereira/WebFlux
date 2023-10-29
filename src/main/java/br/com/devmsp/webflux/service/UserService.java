package br.com.devmsp.webflux.service;

import br.com.devmsp.webflux.entity.User;
import br.com.devmsp.webflux.mapper.UserMapper;
import br.com.devmsp.webflux.model.request.UserRequest;
import br.com.devmsp.webflux.repository.UserRepository;
import br.com.devmsp.webflux.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public Mono<User> save(final UserRequest request){
       return userRepository.save(mapper.toEntity(request));
    }

    public Mono<User> findById(final String id){
        return handleNotFound(userRepository.findById(id), id);
    }

    public Flux<User> finddAll(){
        return userRepository.findAll();
    }

    public Mono<User> update(final String id, final UserRequest request){
        return findById(id)
                .map(entity -> mapper.toEntity(request, entity))
                .flatMap(userRepository::save);
    }

    public Mono<User> delete(final String id) {
        return handleNotFound(userRepository.findAndRemove(id), id);

    }

        private <T> Mono<T> handleNotFound(Mono<T> mono, String id){
            return mono.switchIfEmpty(Mono.error(
                    new ObjectNotFoundException(
                            String.format("Object not found. id: %s, Type: %s", id, User.class.getSimpleName())
                    )
            ));
        }

}
