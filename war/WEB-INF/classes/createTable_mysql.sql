
drop database drp;
 create database drp;
 use drp ;
 alter table CLIENT
   drop foreign key  FK_CLIENT_REFERENCE_REGION;
 
 
alter table CLIENT
   drop foreign key   FK_CLIENT_REFERENCE_DATA_DIC;

alter table FLOWCARD
   drop foreign key   FK_T_FLOWCA_REFERENCE_T_CLIEN2;

alter table FLOWCARD
   drop foreign key   FK_T_FLOWCA_REFERENCE_T_USER3;

alter table FLOWCARD
   drop foreign key   FK_T_FLOWCA_REFERENCE_T_USER4;

alter table FLOWCARD
   drop foreign key   FK_FLOWCARD_REFERENCE_USER_;

alter table FLOWCARD
   drop foreign key   FK_FLOWCARD_REFERENCE_FISCALPE;

alter table FLOWCARD
   drop foreign key   FK_T_FLOWCA_REFERENCE_T_USER2;

alter table FLOWCARD_DETAIL
   drop foreign key   FK_FLOWCARD_REFERENCE_MATERIAL;

alter table FLOWCARD_DETAIL
   drop foreign key   FK_FLOWCARD_REFERENCE_CLIENT;

alter table FLOWCARD_DETAIL
   drop foreign key   FK_FLOWCARD_REFERENCE_FLOWCARD;

alter table INVENTORY
   drop foreign key   FK_INVENTOR_REFERENCE_CLIENT;

alter table INVENTORY
   drop foreign key   FK_INVENTOR_REFERENCE_MATERIAL;

alter table INVENTORY
   drop foreign key   FK_INVENTOR_REFERENCE_FISCALPE;

alter table MATERIAL
   drop foreign key   FK_T_MATERI_REFERENCE_DATA_DI2;

alter table MATERIAL
   drop foreign key   FK_MATERIAL_REFERENCE_DATA_DIC;
drop view  if exists  V_DISTRIBTYPE;

drop view  if exists V_TERMINALTYPE;

drop table  if exists CLIENT cascade  ;

drop table   if exists DATA_DICT cascade  ;

drop table    if exists FISCALPERIOD cascade  ;

drop table  if exists FLOWCARD cascade  ;

drop table  if exists FLOWCARD_DETAIL cascade  ;

drop table   if exists INVENTORY cascade  ;

drop table  if exists MATERIAL cascade  ;

drop table  if exists REGION cascade  ;

drop table  if exists USER_ cascade  ;

 
create table CLIENT  (
   ID                   char(32)                        not null,
   REGIONID             CHAR(32),
   CATEGORY_ID          CHAR(32) comment ',如一级分销商，二级分销商',
   CLIENTNO             VARCHAR(32),
   NAME                 VARCHAR(45),
   BANKNO               int(11),
   PHONE                char(15),
   ADDRESS              VARCHAR(50),
   ZIPCODE              VARCHAR(15),
   CONTACTOR            VARCHAR(30)  comment '联系 人，终端客户所有字段（分销商没有此字段，为null）',
   IS_DISTRIBUTION_FLAG char(1)  comment '是否为分销商 D:是分销商C：不是分销商，即为终端用户',
   constraint PK_CLIENT primary key (ID)
) comment '分销商和终端分销商信息，与终端客户信息';
 
 
create table DATA_DICT  (
   ID                   CHAR(32)                        not null,
   NAME                 VARCHAR(30) COMMENT '类型名，如一级分销商，二级分销商',
   TYPE_ID              char(1)  ,
   constraint PK_DATA_DICT primary key (ID)
) COMMENT '分销商类型 终端客户类型， 物料的类别 物料单位 用type_id 区分 分销商类型，终端客户类型，物料单位';

 

 
 
 
create table FISCALPERIOD  (
   ID                   char(32)                        not null,
   YEAR                 int(4),
   MONTH                int(2),
   STARTDATE            date,
   ENDDATE              date,
   STATUS               char(1) comment '核算期状态 Y：可用 N：不可用',
   constraint PK_FISCALPERIOD primary key (ID)
) comment '会计核算期间维护表';

 
 
 
create table FLOWCARD  (
   ID                   char(32)                        not null,
   STATUS               char(1)                         not null comment 'L:刚录入（数据录人员完成） S:录入后并且已经送审（数据录入人员送审给其他 人审查此流向单）',
   FISCALPERIODID       char(32)                        not null,
   DISTRI_ID            char(32)                        not null,
   RECORDER_ID          char(32)                        not null,
   RECORDER_DATE        date                            not null,
   SPOTTER_ID           char(32),
   SPOTDATE             DATE,
   SPOTTER_DESC         VARCHAR(50),
   ADJUSTER_ID          char(32),
   ADJUSTER_DATE        date,
   FUSHEN_ID            char(32),
   FUSHEN_DATE          date,
   FLAG                 char(1) comment '此表记录了两种类型的信息，以此字段加以区分 O:流向单信息 P：盘点信息 ',
   FLOWCARD_NO          VARCHAR(12),
   constraint PK_FLOWCARD primary key (ID)
) comment '流向单主表';

 

 

 
create table FLOWCARD_DETAIL  (
   ID                   char(32)                        not null,
   FLOWCARD_ID          char(32),
   MATERIAL_ID          char(32),
   MATERIAL_COUNT       float(12,2)                   default 0 ,
   CLIENT_ID            char(32) comment '包括分销商和终端客户  两种需方客户' ,
   ADJUST_COUNT         float(12,2)                   default 0.00,
   ADJUST_REASON        VARCHAR(100),
   constraint PK_FLOWCARD_DETAIL primary key (ID)
)comment '流向单明细信息';


 

 
create table INVENTORY  (
   ID                   char(32)                        not null,
   DISTRI_ID            char(32)                        not null,
   MAT_ID               char(32)                        not null,
   FISCALPERIODID       char(32),
   INITCOUNT            float(12,2)                   default 0,
   INCOUNT              float(12,2)                   default 0,
   OUTCOUNT             float(12,2)                   default 0  comment '库存信息表台帐信息,期末余额=期初数量减入库数量减出库数量',
   STATUS               char(1)                        default 'N' comment '录入人员录入之后须经确认才可使用 Y:可用，确认状态 N不可用，初始化状态',
     primary key (ID)
);
 

 

create table MATERIAL  (
   ID                   char(32)                        not null,
   NO                   VARCHAR(32),
   NAME                 VARCHAR(30),
   GUIGE                VARCHAR(30),
   XINGHAO              VARCHAR(30),
   TYPE_ID              char(32),
   UNITTYPE             char(32),
   PICT_FILENAME        VARCHAR(50),
   constraint PK_MATERIAL primary key (ID)
) comment '物料维护';
 

create table REGION  (
   ID                   CHAR(32)                        not null,
   PID                  CHAR(32),
   NAME                 VARCHAR(30),
   LEVEL_               int(2),
   LEAF_                int(1),
   constraint PK_REGION primary key (ID)
) comment '地区';

 

create table USER_  (
   ID                   char(32)                        not null,
   NAME                 VARCHAR(10),
   PHONE                char(15),
   EMAIL                VARCHAR(35),
   PASSWORD             VARCHAR(18),
   CREATEDATE           date,
   primary key (ID)
) comment '用户信息';

create  or replace  view V_DISTRIBTYPE as
 select  id ,name from DATA_DICT where type_id='D';
 


create or replace view V_TERMINALTYPE as
 select  id ,name from DATA_DICT where type_id='T'  ;
 


alter table CLIENT
   add constraint FK_CLIENT_REFERENCE_REGION foreign key (REGIONID)
      references REGION (ID);

alter table CLIENT
   add constraint FK_CLIENT_REFERENCE_DATA_DIC foreign key (CATEGORY_ID)
      references DATA_DICT (ID);

alter table FLOWCARD
   add constraint FK_T_FLOWCA_REFERENCE_T_CLIEN2 foreign key (DISTRI_ID)
      references CLIENT (ID);

alter table FLOWCARD
   add constraint FK_T_FLOWCA_REFERENCE_T_USER3 foreign key (SPOTTER_ID)
      references USER_ (ID);

alter table FLOWCARD
   add constraint FK_T_FLOWCA_REFERENCE_T_USER4 foreign key (ADJUSTER_ID)
      references USER_ (ID);

alter table FLOWCARD
   add constraint FK_FLOWCARD_REFERENCE_USER_ foreign key (FUSHEN_ID)
      references USER_ (ID);

alter table FLOWCARD
   add constraint FK_FLOWCARD_REFERENCE_FISCALPE foreign key (FISCALPERIODID)
      references FISCALPERIOD (ID);

alter table FLOWCARD
   add constraint FK_T_FLOWCA_REFERENCE_T_USER2 foreign key (RECORDER_ID)
      references USER_ (ID);

alter table FLOWCARD_DETAIL
   add constraint FK_FLOWCARD_REFERENCE_MATERIAL foreign key (MATERIAL_ID)
      references MATERIAL (ID);

alter table FLOWCARD_DETAIL
   add constraint FK_FLOWCARD_REFERENCE_CLIENT foreign key (CLIENT_ID)
      references CLIENT (ID);

alter table FLOWCARD_DETAIL
   add constraint FK_FLOWCARD_REFERENCE_FLOWCARD foreign key (FLOWCARD_ID)
      references FLOWCARD (ID);

alter table INVENTORY
   add constraint FK_INVENTOR_REFERENCE_CLIENT foreign key (DISTRI_ID)
      references CLIENT (ID);

alter table INVENTORY
   add constraint FK_INVENTOR_REFERENCE_MATERIAL foreign key (MAT_ID)
      references MATERIAL (ID);

alter table INVENTORY
   add constraint FK_INVENTOR_REFERENCE_FISCALPE foreign key (FISCALPERIODID)
      references FISCALPERIOD (ID);

alter table MATERIAL
   add constraint FK_T_MATERI_REFERENCE_DATA_DI2 foreign key (TYPE_ID)
      references DATA_DICT (ID);

alter table MATERIAL
   add constraint FK_MATERIAL_REFERENCE_DATA_DIC foreign key (UNITTYPE)
      references DATA_DICT (ID);

 