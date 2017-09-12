-- begin PIVOTTABLELAZYLOAD_TIP_INFO
create table PIVOTTABLELAZYLOAD_TIP_INFO (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TOTAL_BILL decimal(19, 2) not null,
    TIP decimal(19, 2) not null,
    SMOKER boolean not null,
    SIZE_ integer not null,
    SEX varchar(50) not null,
    DAY_ varchar(50) not null,
    TIME_ varchar(50) not null,
    --
    primary key (ID)
)^
-- end PIVOTTABLELAZYLOAD_TIP_INFO
