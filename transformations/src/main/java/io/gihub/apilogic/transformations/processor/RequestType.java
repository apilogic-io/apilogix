package io.gihub.apilogic.transformations.processor;

public enum RequestType {

    SOAP("soap"), REST("rest");

    private final String name;

    RequestType(String name) {
        this.name = name;
    }

    public static RequestType getRequestType(String name) {
        for (RequestType requestType: RequestType.values()) {
            if(name.equals(requestType.getName())) {
                return requestType;
            }
        }
        return REST;
    }

    public String getName() {
        return name;
    }
}
