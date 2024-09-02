# Event Driven Application

This application lays the foundation for a CRUD application based on Events.

## Motivation

When we have CRUD applications the approach of having services that performs various logics along with CRUD operations
Feels off. Therefore this POC brings about a different view of an application, be it monolith or microservice-oriented:
The logic that is related to behavior that should happen when entities get saved/deleted should be handled as events,
should not be coupled in the CRUD classes themselves.

## Technical Details
A few pieces must be combined to achieve a fully event-driven application:

## Why not use Springs out-of-the-box events?
That was the initial idea since spring offers the ```@TransactionEventListener``` and all those goodies, however 
implementing it manually meant one less GenericParam for```AbstraceCrudService```as well as one extra implementation. 
Also, if ```CrudService``` implementation makes no use of events we'll have to provide the structure for it anyway.

### AbstractCrudService class
The ```AbstractCrudService``` class performs it's operations and triggers events so that listeners can decide if 
they should do anything or when they should do it. The classes that extend it must receive a list of ```CrudEventHandler```
implementations that will subscribe to the ```CrudService```.

### CrudEventHandler
This is an interface with a generic param that will represent the entity being saved. Ex:

```java
public interface OperationCrudEventHandler extends CrudEventHandler<Operation> {

}
```
Then implement that interface to handle the operation:
```java
@Slf4j
@RequiredArgsConstructor
@Service
@CrudEventListener(supportedOperationTypes = {CrudOperationType.CREATE}, order = 0)
public class PortfolioManager implements OperationCrudEventHandler {
    private final PortfolioRepository portfolioRepository;

    @Async
    @Override
    public void onPerformOperation(Object source, CrudOperationType operationType, Operation entity) {
        log.info("Will maintain portfolio");
    }
}
```

Now the class that extends ```AbstractCrudService``` will receive a list of listeners:

```java
@Slf4j
@Service
public class PrimaryOperationCrudService
        extends AbstractCrudService<OperationDto, Operation, UUID, OperationRepository, OperationEntityMapper>
        implements OperationCrudService {

    public PrimaryOperationCrudService(OperationRepository repository, 
                                       OperationEntityMapper mapper,
                                       List<OperationCrudEventHandler> listeners) {
        super(repository, mapper, listeners);
    }
}
```

## CrudEventListener annotation

You can use this annotation to resolve if the handler is supported and the order it will be invoked. Alternatively, 
you can implement the methods ```supports``` and ```order``` in the handler class itself. Ex:

```java
@CrudEventListener(supportedOperationTypes = {CrudOperationType.CREATE}, order = 0)
public class PortfolioManager implements OperationCrudEventHandler { ... }
```

```java
@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioManager implements OperationCrudEventHandler {
    private final PortfolioRepository portfolioRepository;

    @Async
    @Override
    public void onPerformOperation(Object source, CrudOperationType operationType, Operation entity) {
        log.info("Will maintain portfolio");
    }

    @Override
    public boolean supports(CrudOperationType operationType, Operation entity) {
        return Arrays.asList(CrudOperationType.CREATE, CrudOperationType.DELETE).contains(operationType);
    }

    @Override
    public int order() {
        return 0;
    }
}

```

## Proposed packaging organization

```text
.
├── EventDrivenApplication.java
├── adapters
├── common #Interfaces, DTOs, Exceptions, Enums.
│   ├── CrudService.java
│   ├── OperationCrudService.java
│   ├── SecurityCrudService.java
│   ├── dataobject
│   │   ├── OperationDto.java
│   │   └── SecurityDto.java
│   └── exceptions
│       ├── CustomException.java
│       └── EntityNotFoundException.java
├── core
│   ├── adapters # Mappers
│   │   ├── EntityMapper.java
│   │   ├── OperationEntityMapper.java
│   │   └── SecurityEntityMapper.java
│   ├── events
│   │   ├── CrudEventHandler.java
│   │   ├── CrudEventListener.java
│   │   ├── CrudOperationType.java
│   │   ├── OperationCrudEventHandler.java
│   │   └── SecurityCrudEventHandler.java
│   ├── model #Entities
│   │   ├── BaseEntity.java
│   │   ├── Operation.java
│   │   ├── Portfolio.java
│   │   ├── Sector.java
│   │   ├── Security.java
│   │   └── SecurityHistory.java
│   ├── repository
│   │   ├── OperationRepository.java
│   │   ├── PortfolioRepository.java
│   │   ├── SectorRepository.java
│   │   └── SecurityRepository.java
│   └── service #Service Implementations
│       ├── AbstractCrudService.java
│       ├── PortfolioManager.java
│       ├── PrimaryOperationCrudService.java
│       └── PrimarySecurityCrudService.java
└── web
    ├── CustomExceptionHandler.java
    ├── EndPointConstants.java
    ├── HttpUtils.java
    ├── MessageType.java
    ├── api
    │   ├── AbstractCrudApi.java
    │   ├── OperationApi.java
    │   └── SecurityCrudApi.java
    └── config
        └── AsyncConfig.java
```

# Requirements

- Maven
- JDK 21

