package com.codigo.msariasantaurco.domain.aggregates.constants;

public class Constant {
    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_INACTIVE = 0;
    public static final String USU_ADMIN = "AARIAS";
    public static final String REDIS_KEY_OBTENERPERSONA = "MS:REGISTRO:PERSONA:";
    public static final String REDIS_KEY_OBTENEREMPRESA = "MS:REGISTRO:EMPRESA:";
    public static final String TIPO_DOCUMENTO_PERSONA = "DNI";
    public static final String TIPO_DOCUMENTO_EMPRESA = "RUC";

    public static final String CREATE = "CREATE";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
}