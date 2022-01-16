package demo.mikko;

import io.smallrye.graphql.api.ErrorCode;

@ErrorCode("not-found")
public class NotFoundException extends RuntimeException {

    public NotFoundException(String parameterName, String parameterValue) {
        super("Not found with " + parameterName + " of value " + parameterValue);
    }


}
