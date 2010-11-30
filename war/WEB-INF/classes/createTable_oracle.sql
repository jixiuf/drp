/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2010-4-16 20:45:13                           */
/*==============================================================*/


alter table CLIENT
   drop constraint FK_CLIENT_REFERENCE_REGION;

alter table CLIENT
   drop constraint FK_CLIENT_REFERENCE_DATA_DIC;

alter table FLOWCARD
   drop constraint FK_T_FLOWCA_REFERENCE_T_CLIEN2;

alter table FLOWCARD
   drop constraint FK_T_FLOWCA_REFERENCE_T_USER3;

alter table FLOWCARD
   drop constraint FK_T_FLOWCA_REFERENCE_T_USER4;

alter table FLOWCARD
   drop constraint FK_FLOWCARD_REFERENCE_USER_;

alter table FLOWCARD
   drop constraint FK_FLOWCARD_REFERENCE_FISCALPE;

alter table FLOWCARD
   drop constraint FK_T_FLOWCA_REFERENCE_T_USER2;

alter table FLOWCARD_DETAIL
   drop constraint FK_FLOWCARD_REFERENCE_MATERIAL;

alter table FLOWCARD_DETAIL
   drop constraint FK_FLOWCARD_REFERENCE_CLIENT;

alter table FLOWCARD_DETAIL
   drop constraint FK_FLOWCARD_REFERENCE_FLOWCARD;

alter table INVENTORY
   drop constraint FK_INVENTOR_REFERENCE_CLIENT;

alter table INVENTORY
   drop constraint FK_INVENTOR_REFERENCE_MATERIAL;

alter table INVENTORY
   drop constraint FK_INVENTOR_REFERENCE_FISCALPE;

alter table MATERIAL
   drop constraint FK_T_MATERI_REFERENCE_DATA_DI2;

alter table MATERIAL
   drop constraint FK_MATERIAL_REFERENCE_DATA_DIC;

drop view V_DISTRIBTYPE;

drop view V_TERMINALTYPE;

drop table CLIENT cascade constraints;

drop table DATA_DICT cascade constraints;

drop table FISCALPERIOD cascade constraints;

drop table FLOWCARD cascade constraints;

drop table FLOWCARD_DETAIL cascade constraints;

drop table INVENTORY cascade constraints;

drop table MATERIAL cascade constraints;

drop table REGION cascade constraints;

drop table USER_ cascade constraints;

/*==============================================================*/
/* Table: CLIENT                                                */
/*==============================================================*/
create table CLIENT  (
   ID                   char(32)                        not null,
   REGIONID             CHAR(32),
   CATEGORY_ID          CHAR(32),
   CLIENTNO             varchar2(32),
   NAME                 VARCHAR2(45),
   BANKNO               NUMBER,
   PHONE                char(15),
   ADDRESS              varchar2(50),
   ZIPCODE              VARCHAR2(15),
   CONTACTOR            VARCHAR2(30),
   IS_DISTRIBUTION_FLAG char(1),
   constraint PK_CLIENT primary key (ID)
);

comment on table CLIENT is
'�����̺��ն�
��������Ϣ�����ն˿ͻ���Ϣ';

comment on column CLIENT.CATEGORY_ID is
'��һ�������̣�����������';

comment on column CLIENT.CONTACTOR is
'��ϵ �ˣ��ն˿ͻ������ֶΣ�������û�д��ֶΣ�Ϊnull��';

comment on column CLIENT.IS_DISTRIBUTION_FLAG is
'�Ƿ�Ϊ������
D:�Ƿ�����
C�����Ƿ����̣���Ϊ�ն��û�';

/*==============================================================*/
/* Table: DATA_DICT                                             */
/*==============================================================*/
create table DATA_DICT  (
   ID                   CHAR(32)                        not null,
   NAME                 VARCHAR(30),
   TYPE_ID              char(1),
   constraint PK_DATA_DICT primary key (ID)
);

comment on table DATA_DICT is
'���������ͣ���һ�������̣����������� 
�ն˿ͻ����ͣ��磬�׼�ҽԺ���Ҽ�ҽԺ   
���ϵ����material
���ϵ�λunit
��Ҫ������name�ֶ���
��type_id ����������𣬷��������ͣ��ն˿ͻ����ͣ����ϵ�λ';

comment on column DATA_DICT.NAME is
'����������һ�������̣�����������';

comment on column DATA_DICT.TYPE_ID is
'D:��ʾ �Ƿ���������distribution 
C:�ն˿ͻ�client
M:���ϵ����material
U�����ϵ�λ';

/*==============================================================*/
/* Table: FISCALPERIOD                                          */
/*==============================================================*/
create table FISCALPERIOD  (
   ID                   char(32)                        not null,
   YEAR                 number(4),
   MONTH                NUMBER(2),
   STARTDATE            date,
   ENDDATE              date,
   STATUS               char(1),
   constraint PK_FISCALPERIOD primary key (ID)
);

comment on table FISCALPERIOD is
'��ƺ����ڼ�ά����';

comment on column FISCALPERIOD.STATUS is
'������״̬
Y������
N��������';

/*==============================================================*/
/* Table: FLOWCARD                                              */
/*==============================================================*/
create table FLOWCARD  (
   ID                   char(32)                        not null,
   STATUS               char(1)                         not null,
   FISCALPERIODID       char(32)                        not null,
   DISTRI_ID            char(32)                        not null,
   RECORDER_ID          char(32)                        not null,
   RECORDER_DATE        date                            not null,
   SPOTTER_ID           char(32),
   SPOTDATE             DATE,
   SPOTTER_DESC         varchar2(50),
   ADJUSTER_ID          char(32),
   ADJUSTER_DATE        date,
   FUSHEN_ID            char(32),
   FUSHEN_DATE          date,
   FLAG                 char(1),
   FLOWCARD_NO          varchar2(12),
   constraint PK_FLOWCARD primary key (ID)
);

comment on table FLOWCARD is
'��������';

comment on column FLOWCARD.STATUS is
'L:��¼�루����¼��Ա��ɣ�
S:¼������Ѿ���������¼����Ա��������� ���������򵥣�';

comment on column FLOWCARD.FLAG is
'�˱��¼���������͵���Ϣ���Դ��ֶμ������� 
O:������Ϣ
P���̵���Ϣ
';

/*==============================================================*/
/* Table: FLOWCARD_DETAIL                                       */
/*==============================================================*/
create table FLOWCARD_DETAIL  (
   ID                   char(32)                        not null,
   FLOWCARD_ID          char(32),
   MATERIAL_ID          char(32),
   MATERIAL_COUNT       number(12,2)                   default 0,
   CLIENT_ID            char(32),
   ADJUST_COUNT         number(12,2)                   default 0.00,
   ADJUST_REASON        varchar2(100),
   constraint PK_FLOWCARD_DETAIL primary key (ID)
);

comment on table FLOWCARD_DETAIL is
'������ϸ��Ϣ';

comment on column FLOWCARD_DETAIL.CLIENT_ID is
'���������̺��ն˿ͻ�  �����跽�ͻ�';

/*==============================================================*/
/* Table: INVENTORY                                             */
/*==============================================================*/
create table INVENTORY  (
   ID                   char(32)                        not null,
   DISTRI_ID            char(32)                        not null,
   MAT_ID               char(32)                        not null,
   FISCALPERIODID       char(32),
   INITCOUNT            number(12,2)                   default 0,
   INCOUNT              number(12,2)                   default 0,
   OUTCOUNT             number(12,2)                   default 0,
   STATUS               char(1)                        default 'N',
   constraint PK_INVENTORY primary key (ID)
);

comment on table INVENTORY is
' �����Ϣ��̨����Ϣ��
a��ĩ���=�ڳ�����-�������-��������';

comment on column INVENTORY.STATUS is
'¼����Ա¼��֮���뾭ȷ�ϲſ�ʹ��
Y:���ã�ȷ��״̬
N�����ã���ʼ��״̬';

/*==============================================================*/
/* Table: MATERIAL                                              */
/*==============================================================*/
create table MATERIAL  (
   ID                   char(32)                        not null,
   NO                   varchar2(32),
   NAME                 VARCHAR2(30),
   GUIGE                VARCHAR2(30),
   XINGHAO              VARCHAR2(30),
   TYPE_ID              char(32),
   UNITTYPE             char(32),
   PICT_FILENAME        varchar2(50),
   constraint PK_MATERIAL primary key (ID)
);

comment on table MATERIAL is
'����ά��';

/*==============================================================*/
/* Table: REGION                                                */
/*==============================================================*/
create table REGION  (
   ID                   CHAR(32)                        not null,
   PID                  CHAR(32),
   NAME                 VARCHAR2(30),
   LEVEL_               number(2),
   LEAF_                number(1),
   constraint PK_REGION primary key (ID)
);

comment on table REGION is
'����';

/*==============================================================*/
/* Table: USER_                                                 */
/*==============================================================*/
create table USER_  (
   ID                   char(32)                        not null,
   NAME                 varchar2(10),
   PHONE                char(15),
   EMAIL                varchar2(35),
   PASSWORD             varchar2(18),
   CREATEDATE           date,
   constraint PK_USER_ primary key (ID)
);

comment on table USER_ is
'�û���Ϣ';

/*==============================================================*/
/* View: V_DISTRIBTYPE                                          */
/*==============================================================*/
create or replace view V_DISTRIBTYPE as
 select  id ,name from data_dict where type_id='D';

comment on column V_DISTRIBTYPE.NAME is
'����������һ�������̣�����������';

/*==============================================================*/
/* View: V_TERMINALTYPE                                         */
/*==============================================================*/
create or replace view V_TERMINALTYPE as
 select  id ,name from data_dict where type_id='T';

comment on column V_TERMINALTYPE.NAME is
'����������һ�������̣�����������';

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

