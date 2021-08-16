package by.valvik.bannermanagement.service.impl;

import by.valvik.bannermanagement.domain.Request;
import by.valvik.bannermanagement.repository.RequestRepository;
import by.valvik.bannermanagement.error.ErrorAction;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl extends GenericServiceImpl<Request, RequestRepository> {

    public RequestServiceImpl(RequestRepository requestRepository, ErrorAction errorAction) {

        super(requestRepository, errorAction);

    }

}
