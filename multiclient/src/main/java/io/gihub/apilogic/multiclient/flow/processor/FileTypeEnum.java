package io.gihub.apilogic.multiclient.flow.processor;



    public enum FileTypeEnum {
        FLATTEN("flatten"),
        RESPONSE("response"),
        TEMPLATE("template"),
        HTTP_TEMPLATE("http_template"),
        HTTP_AUTH_TEMPLATE("http_auth_template"),
        REQ_TRANSFORMATION("req_transformation"),
        RES_TRANSFORMATION("res_transformation");

        private final String key;

        public String getKey() {
            return key;
        }

        FileTypeEnum(String key) {
            this.key = key;
        }

        public static FileTypeEnum getByKey(String key) {
            for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
                if(fileTypeEnum.key.equals(key)) {
                    return fileTypeEnum;
                }
            }
            throw new RuntimeException();
        }

    }
