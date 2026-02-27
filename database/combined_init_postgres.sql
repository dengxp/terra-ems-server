--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE IF EXISTS ONLY public.ems_benchmark DROP CONSTRAINT IF EXISTS fkuimhqqp60r0iotcwwu346jel;
ALTER TABLE IF EXISTS ONLY public.ems_energy_unit_point DROP CONSTRAINT IF EXISTS fksuy4wdg82ye58frkig3ubp606;
ALTER TABLE IF EXISTS ONLY public.ems_energy_unit_point DROP CONSTRAINT IF EXISTS fkpuo4y0owrgxop99sru6qqbtdf;
ALTER TABLE IF EXISTS ONLY public.sys_user_post DROP CONSTRAINT IF EXISTS fkpjx0gi8xwm66cp1w1jvi4hc57;
ALTER TABLE IF EXISTS ONLY public.sys_user_permission DROP CONSTRAINT IF EXISTS fkoo9oirp69aql5xrde05q948ja;
ALTER TABLE IF EXISTS ONLY public.sys_role_permission DROP CONSTRAINT IF EXISTS fkomxrs8a388bknvhjokh440waq;
ALTER TABLE IF EXISTS ONLY public.ems_energy_unit DROP CONSTRAINT IF EXISTS fknlkr4e061x5t6wx2u6broi9sd;
ALTER TABLE IF EXISTS ONLY public.ems_alarm_config DROP CONSTRAINT IF EXISTS fknguophx1udpdc43j06f6m3wdg;
ALTER TABLE IF EXISTS ONLY public.sys_user_post DROP CONSTRAINT IF EXISTS fkng2mc7xcmyerevvobtw95bmu9;
ALTER TABLE IF EXISTS ONLY public.sys_role_dept DROP CONSTRAINT IF EXISTS fkmdoybh4v5t2ooi48m3307n7fx;
ALTER TABLE IF EXISTS ONLY public.ems_energy_data DROP CONSTRAINT IF EXISTS fkl2rph682la6pe73bnxopxccwn;
ALTER TABLE IF EXISTS ONLY public.ems_cost_policy_binding DROP CONSTRAINT IF EXISTS fkj65jboyv49b49jsk2v9ibcfn6;
ALTER TABLE IF EXISTS ONLY public.ems_meter DROP CONSTRAINT IF EXISTS fkimy8spwpso0kru9e6k2kr5t74;
ALTER TABLE IF EXISTS ONLY public.ems_energy_data DROP CONSTRAINT IF EXISTS fkhxwy6yrxwkmng9culxcvybt25;
ALTER TABLE IF EXISTS ONLY public.sys_user_role DROP CONSTRAINT IF EXISTS fkhh52n8vd4ny9ff4x9fb8v65qx;
ALTER TABLE IF EXISTS ONLY public.ems_meter_point DROP CONSTRAINT IF EXISTS fkeoh88jlbidlicytes7sy48uim;
ALTER TABLE IF EXISTS ONLY public.ems_energy_cost_record DROP CONSTRAINT IF EXISTS fke4enn5gcecv26cv6qqw5v67j4;
ALTER TABLE IF EXISTS ONLY public.sys_user_permission DROP CONSTRAINT IF EXISTS fke1o35okbrlv0wbsq4o8tw6mbp;
ALTER TABLE IF EXISTS ONLY public.ems_price_policy DROP CONSTRAINT IF EXISTS fkdm20pwebfqrjy1x2p78lk7s19;
ALTER TABLE IF EXISTS ONLY public.ems_alarm_record DROP CONSTRAINT IF EXISTS fkde5gs6tyxlc7em2ikb2tjgxh5;
ALTER TABLE IF EXISTS ONLY public.sys_dept DROP CONSTRAINT IF EXISTS fkd5ou5hch26i1tk6m8jc4fpirw;
ALTER TABLE IF EXISTS ONLY public.ems_energy_cost_record DROP CONSTRAINT IF EXISTS fkcr8m7fqdmugsugc5aec8ofv9a;
ALTER TABLE IF EXISTS ONLY public.sys_permission DROP CONSTRAINT IF EXISTS fkbvx60lch0yucxxb6pm8i4s3r5;
ALTER TABLE IF EXISTS ONLY public.sys_user_role DROP CONSTRAINT IF EXISTS fkb40xxfch70f5qnyfw8yme1n1s;
ALTER TABLE IF EXISTS ONLY public.sys_user DROP CONSTRAINT IF EXISTS fkb3pkx0wbo6o8i8lj0gxr37v1n;
ALTER TABLE IF EXISTS ONLY public.ems_price_policy_item DROP CONSTRAINT IF EXISTS fkaujit7a1vuuxapn5k3ki5u3dk;
ALTER TABLE IF EXISTS ONLY public.ems_alarm_config DROP CONSTRAINT IF EXISTS fk_alarm_config_meter_point;
ALTER TABLE IF EXISTS ONLY public.ems_meter_point DROP CONSTRAINT IF EXISTS fk9raqe49pu25u5l2657351qs6u;
ALTER TABLE IF EXISTS ONLY public.sys_role_permission DROP CONSTRAINT IF EXISTS fk9q28ewrhntqeipl1t04kh1be7;
ALTER TABLE IF EXISTS ONLY public.sys_dept DROP CONSTRAINT IF EXISTS fk8wb8qylwmt7squef8hedew4qe;
ALTER TABLE IF EXISTS ONLY public.ems_cost_policy_binding DROP CONSTRAINT IF EXISTS fk8o2swh51gx7j0nfgnq5t4ut1p;
ALTER TABLE IF EXISTS ONLY public.ems_peak_valley_data DROP CONSTRAINT IF EXISTS fk5rnu6abd4lmuhx0qomoybvqs7;
ALTER TABLE IF EXISTS ONLY public.ems_peak_valley_data DROP CONSTRAINT IF EXISTS fk2xxsklymdmvnetmrqbh4v4qfv;
ALTER TABLE IF EXISTS ONLY public.sys_menu DROP CONSTRAINT IF EXISTS fk2jrf4gb0gjqi8882gxytpxnhe;
ALTER TABLE IF EXISTS ONLY public.ems_time_period_price DROP CONSTRAINT IF EXISTS fk2a7o9rx5066t0bxuhqovre0jv;
DROP INDEX IF EXISTS public.idx_user_role_user_id;
DROP INDEX IF EXISTS public.idx_user_role_role_id;
DROP INDEX IF EXISTS public.idx_user_post_user_id;
DROP INDEX IF EXISTS public.idx_user_post_post_id;
DROP INDEX IF EXISTS public.idx_user_permission_user_id;
DROP INDEX IF EXISTS public.idx_user_permission_permission_id;
DROP INDEX IF EXISTS public.idx_user_notice_user_id;
DROP INDEX IF EXISTS public.idx_user_notice_notice_id;
DROP INDEX IF EXISTS public.idx_sys_user_username;
DROP INDEX IF EXISTS public.idx_sys_user_phone;
DROP INDEX IF EXISTS public.idx_sys_post_code;
DROP INDEX IF EXISTS public.idx_sys_oper_log_title;
DROP INDEX IF EXISTS public.idx_sys_oper_log_time;
DROP INDEX IF EXISTS public.idx_sys_oper_log_status;
DROP INDEX IF EXISTS public.idx_sys_menu_parent;
DROP INDEX IF EXISTS public.idx_sys_login_time;
DROP INDEX IF EXISTS public.idx_sys_login_status;
DROP INDEX IF EXISTS public.idx_sys_log_user;
DROP INDEX IF EXISTS public.idx_sys_log_type;
DROP INDEX IF EXISTS public.idx_sys_log_time;
DROP INDEX IF EXISTS public.idx_sys_dept_status;
DROP INDEX IF EXISTS public.idx_sys_dept_parent;
DROP INDEX IF EXISTS public.idx_saving_project_status;
DROP INDEX IF EXISTS public.idx_saving_project_liable;
DROP INDEX IF EXISTS public.idx_role_perm_role_id;
DROP INDEX IF EXISTS public.idx_role_perm_permission_id;
DROP INDEX IF EXISTS public.idx_role_id;
DROP INDEX IF EXISTS public.idx_role_code;
DROP INDEX IF EXISTS public.idx_pvd_meter_point;
DROP INDEX IF EXISTS public.idx_pvd_energy_unit;
DROP INDEX IF EXISTS public.idx_pvd_data_time;
DROP INDEX IF EXISTS public.idx_production_product;
DROP INDEX IF EXISTS public.idx_production_energy_unit;
DROP INDEX IF EXISTS public.idx_production_date;
DROP INDEX IF EXISTS public.idx_product_status;
DROP INDEX IF EXISTS public.idx_product_code;
DROP INDEX IF EXISTS public.idx_price_policy_item_policy;
DROP INDEX IF EXISTS public.idx_price_policy_energy_type;
DROP INDEX IF EXISTS public.idx_price_policy_code;
DROP INDEX IF EXISTS public.idx_policy_type;
DROP INDEX IF EXISTS public.idx_policy_issuing_date;
DROP INDEX IF EXISTS public.idx_permission_id;
DROP INDEX IF EXISTS public.idx_permission_code;
DROP INDEX IF EXISTS public.idx_meter_type;
DROP INDEX IF EXISTS public.idx_meter_point_meter;
DROP INDEX IF EXISTS public.idx_meter_point_energy_type;
DROP INDEX IF EXISTS public.idx_meter_point_code;
DROP INDEX IF EXISTS public.idx_meter_energy_type;
DROP INDEX IF EXISTS public.idx_meter_code;
DROP INDEX IF EXISTS public.idx_knowledge_status;
DROP INDEX IF EXISTS public.idx_knowledge_energy_type;
DROP INDEX IF EXISTS public.idx_energy_unit_type;
DROP INDEX IF EXISTS public.idx_energy_unit_parent;
DROP INDEX IF EXISTS public.idx_energy_unit_code;
DROP INDEX IF EXISTS public.idx_energy_type_code;
DROP INDEX IF EXISTS public.idx_energy_type_category;
DROP INDEX IF EXISTS public.idx_energy_data_type;
DROP INDEX IF EXISTS public.idx_energy_data_time;
DROP INDEX IF EXISTS public.idx_energy_data_point;
DROP INDEX IF EXISTS public.idx_energy_data_composite;
DROP INDEX IF EXISTS public.idx_ecr_record_date;
DROP INDEX IF EXISTS public.idx_ecr_period_type;
DROP INDEX IF EXISTS public.idx_ecr_energy_unit;
DROP INDEX IF EXISTS public.idx_dict_data_type;
DROP INDEX IF EXISTS public.idx_cpb_price_policy;
DROP INDEX IF EXISTS public.idx_cpb_energy_unit;
DROP INDEX IF EXISTS public.idx_benchmark_type;
DROP INDEX IF EXISTS public.idx_benchmark_code;
DROP INDEX IF EXISTS public.idx_alarm_record_time;
DROP INDEX IF EXISTS public.idx_alarm_record_config;
DROP INDEX IF EXISTS public.idx_alarm_limit_type_code;
DROP INDEX IF EXISTS public.idx_alarm_config_meter_point;
DROP INDEX IF EXISTS public.idx_alarm_config_limit;
ALTER TABLE IF EXISTS ONLY public.sys_post DROP CONSTRAINT IF EXISTS ukr5b7w4kya2gmxcc1asyns6odk;
ALTER TABLE IF EXISTS ONLY public.ems_energy_type DROP CONSTRAINT IF EXISTS ukqyk5p9pr4r421rh8un10ddyi6;
ALTER TABLE IF EXISTS ONLY public.sys_user DROP CONSTRAINT IF EXISTS ukpulp17fvich5aby4m0kc820h6;
ALTER TABLE IF EXISTS ONLY public.sys_role DROP CONSTRAINT IF EXISTS ukplpigyqwsqfn7mn66npgf9ftp;
ALTER TABLE IF EXISTS ONLY public.sys_module DROP CONSTRAINT IF EXISTS uknoavfxvjvs053hbvxfixe0gw2;
ALTER TABLE IF EXISTS ONLY public.ems_price_policy DROP CONSTRAINT IF EXISTS ukks5jbvi22h30gn1g1fysder64;
ALTER TABLE IF EXISTS ONLY public.ems_meter_point DROP CONSTRAINT IF EXISTS ukjb553gu8ityk06dbghpfbmroy;
ALTER TABLE IF EXISTS ONLY public.ems_product DROP CONSTRAINT IF EXISTS ukj7ymku309maor470onudrjq3n;
ALTER TABLE IF EXISTS ONLY public.sys_config DROP CONSTRAINT IF EXISTS ukellx5pgxddrdrfyvtfbgxccvl;
ALTER TABLE IF EXISTS ONLY public.sys_user_notice DROP CONSTRAINT IF EXISTS uke8tesnqlq4mwewoyko7609fxb;
ALTER TABLE IF EXISTS ONLY public.ems_alarm_limit_type DROP CONSTRAINT IF EXISTS ukco6bbk2823t0js0qnrd41naxu;
ALTER TABLE IF EXISTS ONLY public.sys_dict_data DROP CONSTRAINT IF EXISTS uk_dict_data_value_type;
ALTER TABLE IF EXISTS ONLY public.ems_meter DROP CONSTRAINT IF EXISTS uk5xsxp2n696rrf80766t933ly8;
ALTER TABLE IF EXISTS ONLY public.ems_benchmark DROP CONSTRAINT IF EXISTS uk541184xedrs6lp0yd95kiplis;
ALTER TABLE IF EXISTS ONLY public.sys_user DROP CONSTRAINT IF EXISTS uk51bvuyvihefoh4kp5syh2jpi4;
ALTER TABLE IF EXISTS ONLY public.sys_permission DROP CONSTRAINT IF EXISTS uk2vm98en2ouht0v15fvef2whp4;
ALTER TABLE IF EXISTS ONLY public.ems_energy_unit DROP CONSTRAINT IF EXISTS uk1dd9o4p4mktlib0tq3gpnl2gu;
ALTER TABLE IF EXISTS ONLY public.sys_user_role DROP CONSTRAINT IF EXISTS sys_user_role_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_user_post DROP CONSTRAINT IF EXISTS sys_user_post_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_user DROP CONSTRAINT IF EXISTS sys_user_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_user_permission DROP CONSTRAINT IF EXISTS sys_user_permission_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_user_notice DROP CONSTRAINT IF EXISTS sys_user_notice_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_role DROP CONSTRAINT IF EXISTS sys_role_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_role_permission DROP CONSTRAINT IF EXISTS sys_role_permission_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_post DROP CONSTRAINT IF EXISTS sys_post_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_permission DROP CONSTRAINT IF EXISTS sys_permission_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_operation_log DROP CONSTRAINT IF EXISTS sys_operation_log_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_notice DROP CONSTRAINT IF EXISTS sys_notice_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_module DROP CONSTRAINT IF EXISTS sys_module_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_menu DROP CONSTRAINT IF EXISTS sys_menu_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_login_log DROP CONSTRAINT IF EXISTS sys_login_log_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_log DROP CONSTRAINT IF EXISTS sys_log_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_dict_type DROP CONSTRAINT IF EXISTS sys_dict_type_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_dict_data DROP CONSTRAINT IF EXISTS sys_dict_data_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_dept DROP CONSTRAINT IF EXISTS sys_dept_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_config DROP CONSTRAINT IF EXISTS sys_config_pkey;
ALTER TABLE IF EXISTS ONLY public.sys_dict_type DROP CONSTRAINT IF EXISTS idx_dict_type;
ALTER TABLE IF EXISTS ONLY public.ems_time_period_price DROP CONSTRAINT IF EXISTS ems_time_period_price_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_production_record DROP CONSTRAINT IF EXISTS ems_production_record_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_product DROP CONSTRAINT IF EXISTS ems_product_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_price_policy DROP CONSTRAINT IF EXISTS ems_price_policy_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_price_policy_item DROP CONSTRAINT IF EXISTS ems_price_policy_item_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_policy DROP CONSTRAINT IF EXISTS ems_policy_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_peak_valley_data DROP CONSTRAINT IF EXISTS ems_peak_valley_data_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_meter_point DROP CONSTRAINT IF EXISTS ems_meter_point_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_meter DROP CONSTRAINT IF EXISTS ems_meter_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_knowledge_article DROP CONSTRAINT IF EXISTS ems_knowledge_article_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_energy_unit_point DROP CONSTRAINT IF EXISTS ems_energy_unit_point_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_energy_unit DROP CONSTRAINT IF EXISTS ems_energy_unit_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_energy_type DROP CONSTRAINT IF EXISTS ems_energy_type_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_energy_saving_project DROP CONSTRAINT IF EXISTS ems_energy_saving_project_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_energy_data DROP CONSTRAINT IF EXISTS ems_energy_data_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_energy_cost_record DROP CONSTRAINT IF EXISTS ems_energy_cost_record_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_cost_policy_binding DROP CONSTRAINT IF EXISTS ems_cost_policy_binding_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_benchmark DROP CONSTRAINT IF EXISTS ems_benchmark_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_alarm_record DROP CONSTRAINT IF EXISTS ems_alarm_record_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_alarm_limit_type DROP CONSTRAINT IF EXISTS ems_alarm_limit_type_pkey;
ALTER TABLE IF EXISTS ONLY public.ems_alarm_config DROP CONSTRAINT IF EXISTS ems_alarm_config_pkey;
SELECT pg_catalog.lo_unlink(oid) FROM pg_catalog.pg_largeobject_metadata WHERE oid = '32793';
SELECT pg_catalog.lo_unlink(oid) FROM pg_catalog.pg_largeobject_metadata WHERE oid = '32794';
SELECT pg_catalog.lo_unlink(oid) FROM pg_catalog.pg_largeobject_metadata WHERE oid = '32795';
SELECT pg_catalog.lo_unlink(oid) FROM pg_catalog.pg_largeobject_metadata WHERE oid = '32796';
SELECT pg_catalog.lo_unlink(oid) FROM pg_catalog.pg_largeobject_metadata WHERE oid = '32797';
SELECT pg_catalog.lo_unlink(oid) FROM pg_catalog.pg_largeobject_metadata WHERE oid = '32798';
SELECT pg_catalog.lo_unlink(oid) FROM pg_catalog.pg_largeobject_metadata WHERE oid = '32799';
SELECT pg_catalog.lo_unlink(oid) FROM pg_catalog.pg_largeobject_metadata WHERE oid = '32839';

ALTER TABLE IF EXISTS public.ems_policy ALTER COLUMN id DROP DEFAULT;
ALTER TABLE IF EXISTS public.ems_energy_saving_project ALTER COLUMN id DROP DEFAULT;
DROP TABLE IF EXISTS public.sys_user_role;
DROP TABLE IF EXISTS public.sys_user_post;
DROP TABLE IF EXISTS public.sys_user_permission;
DROP TABLE IF EXISTS public.sys_user_notice;
DROP TABLE IF EXISTS public.sys_user;
DROP TABLE IF EXISTS public.sys_role_permission;
DROP TABLE IF EXISTS public.sys_role_dept;
DROP TABLE IF EXISTS public.sys_role;
DROP TABLE IF EXISTS public.sys_post;
DROP TABLE IF EXISTS public.sys_permission;
DROP TABLE IF EXISTS public.sys_operation_log;
DROP TABLE IF EXISTS public.sys_notice;
DROP TABLE IF EXISTS public.sys_module;
DROP TABLE IF EXISTS public.sys_menu;
DROP TABLE IF EXISTS public.sys_login_log;
DROP TABLE IF EXISTS public.sys_log;
DROP TABLE IF EXISTS public.sys_dict_type;
DROP TABLE IF EXISTS public.sys_dict_data;
DROP TABLE IF EXISTS public.sys_dept;
DROP TABLE IF EXISTS public.sys_config;
DROP TABLE IF EXISTS public.ems_time_period_price;
DROP TABLE IF EXISTS public.ems_production_record;
DROP TABLE IF EXISTS public.ems_product;
DROP TABLE IF EXISTS public.ems_price_policy_item;
DROP TABLE IF EXISTS public.ems_price_policy;
DROP SEQUENCE IF EXISTS public.ems_policy_id_seq;
DROP TABLE IF EXISTS public.ems_policy;
DROP TABLE IF EXISTS public.ems_peak_valley_data;
DROP TABLE IF EXISTS public.ems_meter_point;
DROP TABLE IF EXISTS public.ems_meter;
DROP TABLE IF EXISTS public.ems_knowledge_article;
DROP TABLE IF EXISTS public.ems_energy_unit_point;
DROP TABLE IF EXISTS public.ems_energy_unit;
DROP TABLE IF EXISTS public.ems_energy_type;
DROP SEQUENCE IF EXISTS public.ems_energy_saving_project_id_seq;
DROP TABLE IF EXISTS public.ems_energy_saving_project;
DROP TABLE IF EXISTS public.ems_energy_data;
DROP TABLE IF EXISTS public.ems_energy_cost_record;
DROP TABLE IF EXISTS public.ems_cost_policy_binding;
DROP TABLE IF EXISTS public.ems_benchmark;
DROP TABLE IF EXISTS public.ems_alarm_record;
DROP TABLE IF EXISTS public.ems_alarm_limit_type;
DROP TABLE IF EXISTS public.ems_alarm_config;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: ems_alarm_config; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_alarm_config (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    is_enabled boolean,
    limit_value numeric(10,4),
    meter_point_id bigint NOT NULL,
    remark character varying(500),
    limit_type_id bigint NOT NULL
);


--
-- Name: ems_alarm_config_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_alarm_config ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_alarm_config_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_alarm_limit_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_alarm_limit_type (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    alarm_type character varying(20),
    color_number character varying(20),
    comparator_operator character varying(10),
    limit_code character varying(50) NOT NULL,
    limit_name character varying(100) NOT NULL
);


--
-- Name: ems_alarm_limit_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_alarm_limit_type ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_alarm_limit_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_alarm_record; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_alarm_record (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    handle_remark character varying(500),
    handle_time timestamp(6) without time zone,
    status integer,
    trigger_time timestamp(6) without time zone NOT NULL,
    trigger_value numeric(10,4),
    config_id bigint NOT NULL
);


--
-- Name: ems_alarm_record_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_alarm_record ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_alarm_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_benchmark; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_benchmark (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    code character varying(50) NOT NULL,
    grade character varying(20),
    name character varying(100) NOT NULL,
    national_num character varying(50),
    remark character varying(500),
    status smallint,
    type character varying(20),
    unit character varying(20),
    value numeric(12,4),
    energy_type_id bigint,
    CONSTRAINT ems_benchmark_status_check CHECK (((status >= 0) AND (status <= 1))),
    CONSTRAINT ems_benchmark_type_check CHECK (((type)::text = ANY ((ARRAY['NATIONAL'::character varying, 'INDUSTRY'::character varying, 'ENTERPRISE'::character varying, 'REGIONAL'::character varying])::text[])))
);


--
-- Name: ems_benchmark_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_benchmark ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_benchmark_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_cost_policy_binding; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_cost_policy_binding (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    effective_end_date date,
    effective_start_date date NOT NULL,
    remark character varying(500),
    status smallint,
    energy_unit_id bigint NOT NULL,
    price_policy_id bigint NOT NULL,
    CONSTRAINT ems_cost_policy_binding_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: ems_cost_policy_binding_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_cost_policy_binding ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_cost_policy_binding_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_energy_cost_record; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_energy_cost_record (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    consumption numeric(14,4),
    cost numeric(14,2),
    flat_consumption numeric(14,4),
    peak_consumption numeric(14,4),
    period_type character varying(10),
    power_factor numeric(5,2),
    record_date date NOT NULL,
    remark character varying(500),
    sharp_consumption numeric(14,4),
    valley_consumption numeric(14,4),
    energy_type_id bigint,
    energy_unit_id bigint,
    CONSTRAINT ems_energy_cost_record_period_type_check CHECK (((period_type)::text = ANY ((ARRAY['DAY'::character varying, 'MONTH'::character varying, 'YEAR'::character varying])::text[])))
);


--
-- Name: ems_energy_cost_record_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_energy_cost_record ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_energy_cost_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_energy_data; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_energy_data (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    data_time timestamp(6) without time zone NOT NULL,
    time_type character varying(10) NOT NULL,
    value numeric(18,4),
    energy_type_id bigint NOT NULL,
    meter_point_id bigint NOT NULL
);


--
-- Name: ems_energy_data_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_energy_data ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_energy_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_energy_saving_project; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_energy_saving_project (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    plan text,
    implementation_plan text,
    current_work character varying(500),
    liable_person character varying(50),
    saving_amount numeric(12,2),
    completion_time date,
    status character varying(20) DEFAULT 'PLANNING'::character varying,
    remark character varying(500),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


--
-- Name: ems_energy_saving_project_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ems_energy_saving_project_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: ems_energy_saving_project_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ems_energy_saving_project_id_seq OWNED BY public.ems_energy_saving_project.id;


--
-- Name: ems_energy_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_energy_type (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    category character varying(20),
    code character varying(50) NOT NULL,
    coefficient numeric(10,4),
    color character varying(20),
    default_price numeric(10,4),
    emission_factor numeric(10,4),
    icon character varying(50),
    name character varying(100) NOT NULL,
    remark character varying(500),
    sort_order integer,
    status smallint NOT NULL,
    storable boolean NOT NULL,
    unit character varying(20) NOT NULL,
    CONSTRAINT ems_energy_type_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: ems_energy_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_energy_type ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_energy_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_energy_unit; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_energy_unit (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    code character varying(50) NOT NULL,
    level integer NOT NULL,
    name character varying(100) NOT NULL,
    remark character varying(500),
    sort_order integer,
    status smallint NOT NULL,
    parent_id bigint,
    rated_current numeric(10,2),
    rated_power numeric(12,2),
    unit_type character varying(20),
    voltage_level character varying(20),
    CONSTRAINT ems_energy_unit_status_check CHECK (((status >= 0) AND (status <= 1))),
    CONSTRAINT ems_energy_unit_unit_type_check CHECK (((unit_type)::text = ANY ((ARRAY['GENERAL'::character varying, 'BRANCH'::character varying, 'PROCESS'::character varying, 'EQUIPMENT'::character varying])::text[])))
);


--
-- Name: ems_energy_unit_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_energy_unit ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_energy_unit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_energy_unit_point; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_energy_unit_point (
    meter_point_id bigint NOT NULL,
    energy_unit_id bigint NOT NULL
);


--
-- Name: ems_knowledge_article; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_knowledge_article (
    id bigint NOT NULL,
    author character varying(50),
    category character varying(50),
    content text,
    energy_type_id bigint,
    sort_order integer,
    status smallint NOT NULL,
    summary character varying(500),
    title character varying(200) NOT NULL,
    view_count integer DEFAULT 0 NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    CONSTRAINT ems_knowledge_article_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: ems_knowledge_article_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_knowledge_article ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_knowledge_article_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_meter; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_meter (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    check_cycle integer,
    code character varying(50) NOT NULL,
    gateway_id character varying(200),
    location character varying(300),
    manufacturer character varying(255),
    max_power character varying(255),
    measure_range character varying(150),
    model_number character varying(150),
    name character varying(150) NOT NULL,
    person_charge character varying(200),
    putrun_time date,
    remark character varying(500),
    reminder_cycle integer,
    start_time date,
    status smallint NOT NULL,
    type character varying(20) NOT NULL,
    wire_diameter character varying(255),
    energy_type_id bigint NOT NULL,
    CONSTRAINT ems_meter_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: ems_meter_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_meter ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_meter_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_meter_point; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_meter_point (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    category character varying(20),
    code character varying(50) NOT NULL,
    initial_value numeric(15,4),
    max_value numeric(15,4),
    min_value numeric(15,4),
    name character varying(100) NOT NULL,
    point_type character varying(20) NOT NULL,
    remark character varying(500),
    sort_order integer,
    status smallint NOT NULL,
    step_max numeric(15,4),
    step_min numeric(15,4),
    unit character varying(20),
    energy_type_id bigint,
    meter_id bigint,
    CONSTRAINT ems_meter_point_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: ems_meter_point_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_meter_point ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_meter_point_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_peak_valley_data; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_peak_valley_data (
    id bigint NOT NULL,
    cost numeric(18,4),
    created_at timestamp(6) without time zone,
    data_time date NOT NULL,
    electricity numeric(18,4),
    energy_unit_id bigint,
    meter_point_id bigint NOT NULL,
    period_type character varying(20) NOT NULL,
    price numeric(10,4),
    time_type character varying(10) NOT NULL,
    CONSTRAINT ems_peak_valley_data_period_type_check CHECK (((period_type)::text = ANY ((ARRAY['SHARP'::character varying, 'PEAK'::character varying, 'FLAT'::character varying, 'VALLEY'::character varying, 'DEEP_VALLEY'::character varying])::text[])))
);


--
-- Name: ems_peak_valley_data_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_peak_valley_data ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_peak_valley_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_policy; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_policy (
    id bigint NOT NULL,
    title character varying(200) NOT NULL,
    type character varying(20) DEFAULT 'NATIONAL'::character varying,
    department character varying(100),
    issuing_date date,
    file_url character varying(500),
    summary text,
    status integer DEFAULT 0,
    remark character varying(500),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


--
-- Name: ems_policy_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ems_policy_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: ems_policy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ems_policy_id_seq OWNED BY public.ems_policy.id;


--
-- Name: ems_price_policy; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_price_policy (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    code character varying(50) NOT NULL,
    is_multi_rate boolean,
    name character varying(100) NOT NULL,
    remark character varying(500),
    sort_order integer,
    status smallint,
    energy_type_id bigint DEFAULT 1,
    effective_end_date date,
    effective_start_date date,
    CONSTRAINT ems_price_policy_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: ems_price_policy_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_price_policy ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_price_policy_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_price_policy_item; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_price_policy_item (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    period_type character varying(20) NOT NULL,
    price numeric(10,4) NOT NULL,
    remark character varying(200),
    sort_order integer,
    policy_id bigint NOT NULL,
    end_time character varying(10),
    start_time character varying(10),
    CONSTRAINT ems_price_policy_item_period_type_check CHECK (((period_type)::text = ANY ((ARRAY['SHARP'::character varying, 'PEAK'::character varying, 'FLAT'::character varying, 'VALLEY'::character varying, 'DEEP'::character varying])::text[])))
);


--
-- Name: ems_price_policy_item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_price_policy_item ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_price_policy_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_product; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_product (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    code character varying(50) NOT NULL,
    name character varying(100) NOT NULL,
    remark character varying(500),
    sort_order integer,
    status smallint NOT NULL,
    type character varying(50),
    unit character varying(20),
    CONSTRAINT ems_product_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: ems_product_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_product ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_production_record; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_production_record (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    energy_unit_id bigint NOT NULL,
    granularity character varying(20) NOT NULL,
    product_name character varying(100) NOT NULL,
    quantity numeric(18,4) NOT NULL,
    record_date timestamp without time zone NOT NULL,
    remark character varying(500),
    unit character varying(20),
    data_type character varying(10) NOT NULL,
    product_type character varying(50),
    CONSTRAINT ems_production_record_granularity_check CHECK (((granularity)::text = ANY ((ARRAY['HOUR'::character varying, 'DAY'::character varying, 'MONTH'::character varying, 'YEAR'::character varying])::text[])))
);


--
-- Name: ems_production_record_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_production_record ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_production_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ems_time_period_price; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ems_time_period_price (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    end_time time(6) without time zone NOT NULL,
    period_name character varying(50) NOT NULL,
    period_type character varying(20) NOT NULL,
    price numeric(10,4) NOT NULL,
    price_policy_id bigint,
    remark character varying(500),
    sort_order integer,
    start_time time(6) without time zone NOT NULL,
    status smallint,
    CONSTRAINT ems_time_period_price_period_type_check CHECK (((period_type)::text = ANY ((ARRAY['SHARP'::character varying, 'PEAK'::character varying, 'FLAT'::character varying, 'VALLEY'::character varying, 'DEEP_VALLEY'::character varying])::text[]))),
    CONSTRAINT ems_time_period_price_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: ems_time_period_price_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.ems_time_period_price ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ems_time_period_price_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_config; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_config (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    config_key character varying(100) NOT NULL,
    config_name character varying(100),
    config_type character varying(1),
    config_value character varying(500),
    remark character varying(500)
);


--
-- Name: sys_config_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_config ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_config_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_dept; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_dept (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    ancestors character varying(255),
    code character varying(32),
    email character varying(64),
    leader character varying(32),
    name character varying(64) NOT NULL,
    phone character varying(11),
    sort_order integer,
    status smallint NOT NULL,
    parent_id bigint,
    manager_id bigint,
    description character varying(512),
    CONSTRAINT sys_dept_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: sys_dept_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_dept ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_dept_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_dict_data; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_dict_data (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    is_default character varying(1),
    label character varying(100) NOT NULL,
    remark character varying(500),
    sort_order bigint,
    status character varying(1),
    tag_color character varying(100),
    tag_type character varying(100),
    type_code character varying(100) NOT NULL,
    value character varying(100) NOT NULL
);


--
-- Name: sys_dict_data_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_dict_data ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_dict_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_dict_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_dict_type (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    name character varying(100) NOT NULL,
    remark character varying(500),
    status character varying(1),
    type character varying(100) NOT NULL
);


--
-- Name: sys_dict_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_dict_type ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_dict_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_log; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_log (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    business_type integer,
    cost_time bigint,
    dept_name character varying(50),
    error_msg text,
    ip character varying(128),
    location character varying(255),
    log_type character varying(10) NOT NULL,
    method character varying(100),
    param text,
    request_method character varying(10),
    result text,
    status integer,
    title character varying(50),
    url character varying(255),
    username character varying(50)
);


--
-- Name: sys_log_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_log ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_login_log; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_login_log (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    browser character varying(50),
    ipaddr character varying(128),
    login_location character varying(255),
    login_time timestamp(6) without time zone,
    msg character varying(255),
    os character varying(50),
    status character varying(1),
    user_name character varying(50)
);


--
-- Name: sys_login_log_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_login_log ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_login_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_menu; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_menu (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    component character varying(255),
    icon character varying(100),
    menu_type character varying(1) NOT NULL,
    name character varying(50) NOT NULL,
    path character varying(200),
    permission_code character varying(100),
    sort_order integer,
    status smallint NOT NULL,
    visible character varying(1),
    parent_id bigint,
    CONSTRAINT sys_menu_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: sys_menu_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_menu ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_module; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_module (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    code character varying(64) NOT NULL,
    name character varying(128) NOT NULL,
    sort_order integer
);


--
-- Name: sys_module_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_module ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_module_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_notice; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_notice (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    notice_content oid,
    notice_title character varying(50) NOT NULL,
    notice_type character varying(1) NOT NULL,
    remark character varying(255),
    status smallint NOT NULL,
    CONSTRAINT sys_notice_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: sys_notice_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_notice ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_notice_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_operation_log; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_operation_log (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    business_type integer,
    cost_time bigint,
    dept_name character varying(50),
    error_msg text,
    json_result text,
    method character varying(100),
    oper_ip character varying(128),
    oper_location character varying(255),
    oper_name character varying(50),
    oper_param text,
    oper_time timestamp(6) without time zone,
    oper_url character varying(255),
    operator_type integer,
    request_method character varying(10),
    status integer,
    title character varying(50),
    operation_ip character varying(128),
    operation_location character varying(255),
    operation_name character varying(50),
    operation_param text,
    operation_time timestamp(6) without time zone,
    operation_url character varying(255)
);


--
-- Name: sys_operation_log_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_operation_log ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_operation_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_permission; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_permission (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    code character varying(64) NOT NULL,
    description character varying(500),
    name character varying(128) NOT NULL,
    super_permission boolean,
    module_id bigint
);


--
-- Name: sys_permission_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_permission ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_post; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_post (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    code character varying(64) NOT NULL,
    name character varying(64) NOT NULL,
    remark character varying(500),
    sort_order integer,
    status smallint NOT NULL,
    CONSTRAINT sys_post_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: sys_post_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_post ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_post_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_role (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    code character varying(64) NOT NULL,
    member_count integer DEFAULT 0 NOT NULL,
    name character varying(128) NOT NULL,
    permission_count integer DEFAULT 0 NOT NULL,
    remark character varying(500),
    data_scope character varying(20),
    status smallint NOT NULL,
    CONSTRAINT sys_role_data_scope_check CHECK (((data_scope)::text = ANY ((ARRAY['ALL'::character varying, 'DEPT'::character varying, 'DEPT_AND_CHILD'::character varying, 'SELF'::character varying, 'CUSTOM'::character varying])::text[]))),
    CONSTRAINT sys_role_status_check CHECK (((status >= 0) AND (status <= 1)))
);


--
-- Name: sys_role_dept; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_role_dept (
    role_id bigint NOT NULL,
    dept_id bigint
);


--
-- Name: sys_role_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_role ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_role_permission; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_role_permission (
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL
);


--
-- Name: sys_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_user (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    email character varying(128),
    nickname character varying(64),
    password character varying(128) NOT NULL,
    phone character varying(20),
    status integer NOT NULL,
    username character varying(64) NOT NULL,
    account_expire_at timestamp(6) without time zone,
    credentials_expire_at timestamp(6) without time zone,
    fail_login_count integer DEFAULT 0 NOT NULL,
    last_login_at timestamp(6) without time zone,
    avatar character varying(256),
    dept_id bigint,
    employee_no character varying(32),
    gender smallint,
    remark character varying(512),
    real_name character varying(64),
    super_admin boolean,
    CONSTRAINT sys_user_gender_check CHECK (((gender >= 0) AND (gender <= 2)))
);


--
-- Name: sys_user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_user ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_user_notice; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_user_notice (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    notice_id bigint NOT NULL,
    read_time timestamp(6) without time zone,
    user_id bigint NOT NULL
);


--
-- Name: sys_user_notice_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.sys_user_notice ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.sys_user_notice_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: sys_user_permission; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_user_permission (
    user_id bigint NOT NULL,
    permission_id bigint NOT NULL
);


--
-- Name: sys_user_post; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_user_post (
    user_id bigint NOT NULL,
    post_id bigint NOT NULL
);


--
-- Name: sys_user_role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sys_user_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


--
-- Name: ems_energy_saving_project id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_saving_project ALTER COLUMN id SET DEFAULT nextval('public.ems_energy_saving_project_id_seq'::regclass);


--
-- Name: ems_policy id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_policy ALTER COLUMN id SET DEFAULT nextval('public.ems_policy_id_seq'::regclass);


--
-- Name: 32793..32839; Type: BLOB METADATA; Schema: -; Owner: -
--

SELECT pg_catalog.lo_create('32793');
SELECT pg_catalog.lo_create('32794');
SELECT pg_catalog.lo_create('32795');
SELECT pg_catalog.lo_create('32796');
SELECT pg_catalog.lo_create('32797');
SELECT pg_catalog.lo_create('32798');
SELECT pg_catalog.lo_create('32799');
SELECT pg_catalog.lo_create('32839');

--
-- Data for Name: ems_alarm_config; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_alarm_config (id, created_at, updated_at, is_enabled, limit_value, meter_point_id, remark, limit_type_id) VALUES (21, '2026-01-05 02:38:35.46676', '2026-01-05 02:38:35.46676', true, 1000.0000, 1, '日用电量超过1000kWh时预警', 17);
INSERT INTO public.ems_alarm_config (id, created_at, updated_at, is_enabled, limit_value, meter_point_id, remark, limit_type_id) VALUES (22, '2026-01-05 02:38:35.48787', '2026-01-05 02:38:35.48787', true, 1500.0000, 1, '日用电量超过1500kWh时报警', 21);
INSERT INTO public.ems_alarm_config (id, created_at, updated_at, is_enabled, limit_value, meter_point_id, remark, limit_type_id) VALUES (23, '2026-01-05 02:38:35.496786', '2026-01-05 02:38:35.496786', true, 500.0000, 3, '瞬时功率超过500kW时预警', 17);
INSERT INTO public.ems_alarm_config (id, created_at, updated_at, is_enabled, limit_value, meter_point_id, remark, limit_type_id) VALUES (24, '2026-01-05 02:38:35.520265', '2026-01-05 02:38:35.520265', true, 800.0000, 3, '瞬时功率超过800kW时报警', 21);
INSERT INTO public.ems_alarm_config (id, created_at, updated_at, is_enabled, limit_value, meter_point_id, remark, limit_type_id) VALUES (25, '2026-01-05 02:38:35.536415', '2026-01-05 02:38:35.536415', true, 0.9000, 11, '功率因数低于0.9时预警', 18);
INSERT INTO public.ems_alarm_config (id, created_at, updated_at, is_enabled, limit_value, meter_point_id, remark, limit_type_id) VALUES (26, '2026-01-05 02:38:35.54798', '2026-01-05 02:38:35.54798', true, 0.8500, 11, '功率因数低于0.85时报警', 22);
INSERT INTO public.ems_alarm_config (id, created_at, updated_at, is_enabled, limit_value, meter_point_id, remark, limit_type_id) VALUES (27, '2026-01-05 02:38:35.563494', '2026-01-05 02:38:35.563494', true, 100.0000, 5, 'A相电流超过100A时报警', 23);
INSERT INTO public.ems_alarm_config (id, created_at, updated_at, is_enabled, limit_value, meter_point_id, remark, limit_type_id) VALUES (28, '2026-01-05 02:38:35.584133', '2026-01-05 02:38:35.584133', true, 100.0000, 12, '日用水量超过100m³时预警', 17);
INSERT INTO public.ems_alarm_config (id, created_at, updated_at, is_enabled, limit_value, meter_point_id, remark, limit_type_id) VALUES (29, '2026-01-05 02:38:35.603059', '2026-01-05 02:38:35.603059', true, 1.0000, 21, '蒸汽压力超过1.0MPa时预警', 17);
INSERT INTO public.ems_alarm_config (id, created_at, updated_at, is_enabled, limit_value, meter_point_id, remark, limit_type_id) VALUES (30, '2026-01-05 02:38:35.615522', '2026-01-05 02:38:35.615522', true, 0.3000, 21, '蒸汽压力低于0.3MPa时预警', 18);


--
-- Data for Name: ems_alarm_limit_type; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_alarm_limit_type (id, created_at, updated_at, alarm_type, color_number, comparator_operator, limit_code, limit_name) VALUES (17, '2026-01-05 02:38:35.445999', '2026-01-05 02:38:35.445999', 'WARNING', '#faad14', '>', 'HI_WARN', '高值预警');
INSERT INTO public.ems_alarm_limit_type (id, created_at, updated_at, alarm_type, color_number, comparator_operator, limit_code, limit_name) VALUES (18, '2026-01-05 02:38:35.445999', '2026-01-05 02:38:35.445999', 'WARNING', '#1890ff', '<', 'LO_WARN', '低值预警');
INSERT INTO public.ems_alarm_limit_type (id, created_at, updated_at, alarm_type, color_number, comparator_operator, limit_code, limit_name) VALUES (19, '2026-01-05 02:38:35.445999', '2026-01-05 02:38:35.445999', 'WARNING', '#fa8c16', '>=', 'HH_WARN', '偏高预警');
INSERT INTO public.ems_alarm_limit_type (id, created_at, updated_at, alarm_type, color_number, comparator_operator, limit_code, limit_name) VALUES (20, '2026-01-05 02:38:35.445999', '2026-01-05 02:38:35.445999', 'WARNING', '#13c2c2', '<=', 'LL_WARN', '偏低预警');
INSERT INTO public.ems_alarm_limit_type (id, created_at, updated_at, alarm_type, color_number, comparator_operator, limit_code, limit_name) VALUES (21, '2026-01-05 02:38:35.445999', '2026-01-05 02:38:35.445999', 'ALARM', '#ff4d4f', '>', 'HI_HI', '高高报警');
INSERT INTO public.ems_alarm_limit_type (id, created_at, updated_at, alarm_type, color_number, comparator_operator, limit_code, limit_name) VALUES (22, '2026-01-05 02:38:35.445999', '2026-01-05 02:38:35.445999', 'ALARM', '#f5222d', '<', 'LO_LO', '低低报警');
INSERT INTO public.ems_alarm_limit_type (id, created_at, updated_at, alarm_type, color_number, comparator_operator, limit_code, limit_name) VALUES (23, '2026-01-05 02:38:35.445999', '2026-01-05 02:38:35.445999', 'ALARM', '#eb2f96', '>=', 'OVER_LIMIT', '超限报警');
INSERT INTO public.ems_alarm_limit_type (id, created_at, updated_at, alarm_type, color_number, comparator_operator, limit_code, limit_name) VALUES (24, '2026-01-05 02:38:35.445999', '2026-01-05 02:38:35.445999', 'ALARM', '#722ed1', '<=', 'UNDER_LIMIT', '欠限报警');


--
-- Data for Name: ems_alarm_record; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_alarm_record (id, created_at, updated_at, handle_remark, handle_time, status, trigger_time, trigger_value, config_id) VALUES (1, '2026-01-05 02:38:35.64304', '2026-01-05 02:38:35.64304', '已通知生产部门降低负荷', '2025-12-31 03:08:35.64304', 1, '2025-12-31 02:38:35.64304', 1650.0000, 22);
INSERT INTO public.ems_alarm_record (id, created_at, updated_at, handle_remark, handle_time, status, trigger_time, trigger_value, config_id) VALUES (2, '2026-01-05 02:38:35.679872', '2026-01-05 02:38:35.679872', '已调整无功补偿装置', '2026-01-02 04:38:35.679872', 1, '2026-01-02 02:38:35.679872', 0.8200, 26);
INSERT INTO public.ems_alarm_record (id, created_at, updated_at, handle_remark, handle_time, status, trigger_time, trigger_value, config_id) VALUES (3, '2026-01-05 02:38:35.693506', '2026-01-05 02:38:35.693506', '生产高峰期正常波动，继续观察', '2026-01-03 02:53:35.693506', 1, '2026-01-03 02:38:35.693506', 520.0000, 23);
INSERT INTO public.ems_alarm_record (id, created_at, updated_at, handle_remark, handle_time, status, trigger_time, trigger_value, config_id) VALUES (4, '2026-01-05 02:38:35.70763', '2026-01-05 02:38:35.70763', NULL, NULL, 0, '2026-01-04 02:38:35.70763', 115.0000, 27);
INSERT INTO public.ems_alarm_record (id, created_at, updated_at, handle_remark, handle_time, status, trigger_time, trigger_value, config_id) VALUES (5, '2026-01-05 02:38:35.720118', '2026-01-05 02:38:35.720118', NULL, NULL, 0, '2026-01-04 20:38:35.720118', 850.0000, 24);
INSERT INTO public.ems_alarm_record (id, created_at, updated_at, handle_remark, handle_time, status, trigger_time, trigger_value, config_id) VALUES (6, '2026-01-05 02:38:35.737418', '2026-01-05 02:38:35.737418', NULL, NULL, 0, '2026-01-05 00:38:35.737418', 125.0000, 28);
INSERT INTO public.ems_alarm_record (id, created_at, updated_at, handle_remark, handle_time, status, trigger_time, trigger_value, config_id) VALUES (7, '2026-01-05 02:38:35.7576', '2026-01-05 02:38:35.7576', '设备检修期间，正常现象', '2026-01-01 02:48:35.7576', 2, '2026-01-01 02:38:35.7576', 0.2500, 30);


--
-- Data for Name: ems_benchmark; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (1, '2026-01-06 15:35:39.295235', '2026-01-06 15:35:39.295235', 'GB-ELEC-001', '先进值', '单位产品电耗限额（水泥）', 'GB 16780-2021', '水泥单位产品综合电耗先进值', 0, 'NATIONAL', 'kWh/t', 75.0000, NULL);
INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (2, '2026-01-06 15:35:39.308665', '2026-01-06 15:35:39.308665', 'GB-ELEC-002', '准入值', '单位产品电耗限额（水泥）', 'GB 16780-2021', '水泥单位产品综合电耗准入值', 0, 'NATIONAL', 'kWh/t', 85.0000, NULL);
INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (3, '2026-01-06 15:35:39.318657', '2026-01-06 15:35:39.318657', 'GB-ELEC-003', '先进值', '单位产品电耗限额（钢铁）', 'GB 21256-2021', '粗钢综合电耗先进值', 0, 'NATIONAL', 'kWh/t', 450.0000, NULL);
INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (4, '2026-01-06 15:35:39.326426', '2026-01-06 15:35:39.326426', 'GB-ELEC-004', '准入值', '单位产品电耗限额（钢铁）', 'GB 21256-2021', '粗钢综合电耗准入值', 0, 'NATIONAL', 'kWh/t', 520.0000, NULL);
INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (6, '2026-01-06 15:35:39.341656', '2026-01-06 15:35:39.341656', 'IND-ELEC-002', '2级', '空压机能效限值', 'GB 19153-2019', '空压机2级能效限值', 0, 'INDUSTRY', 'kW/(m³/min)', 7.2000, NULL);
INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (7, '2026-01-06 15:35:39.350858', '2026-01-06 15:35:39.350858', 'IND-ELEC-003', '1级', '电动机能效限值', 'GB 18613-2020', '三相异步电动机1级能效限值(典型值)', 0, 'INDUSTRY', '%', 96.2000, NULL);
INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (8, '2026-01-06 15:35:39.359692', '2026-01-06 15:35:39.359692', 'ENT-ELEC-001', '目标值', '车间单位产出电耗', NULL, '企业内部目标值', 0, 'ENTERPRISE', 'kWh/万元', 120.0000, NULL);
INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (9, '2026-01-06 15:35:39.366133', '2026-01-06 15:35:39.366133', 'ENT-ELEC-002', '目标值', '办公楼单位面积电耗', NULL, '办公楼年度用电目标', 0, 'ENTERPRISE', 'kWh/m²/年', 80.0000, NULL);
INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (10, '2026-01-06 15:35:39.376237', '2026-01-06 15:35:39.376237', 'REG-ELEC-001', '先进值', '广东省工业用电限额', 'DB44/T xxx', '广东省地方标准', 0, 'REGIONAL', 'kWh/万元', 95.0000, NULL);
INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (11, '2026-02-08 11:30:42.248251', '2026-02-08 11:30:42.248251', 'GB-ELEC-0041', 'ddd', '藞dd', NULL, 'adsfsadf', 0, 'NATIONAL', 'asdfasd', NULL, NULL);
INSERT INTO public.ems_benchmark (id, created_at, updated_at, code, grade, name, national_num, remark, status, type, unit, value, energy_type_id) VALUES (12, '2026-02-08 11:58:09.756139', '2026-02-08 11:58:09.756139', 'BM_AUTO_1770523089730', NULL, '自动化指标', NULL, NULL, 1, 'NATIONAL', 'kWh', NULL, NULL);


--
-- Data for Name: ems_cost_policy_binding; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_cost_policy_binding (id, created_at, updated_at, effective_end_date, effective_start_date, remark, status, energy_unit_id, price_policy_id) VALUES (4, '2026-02-09 14:57:23.685592', '2026-02-09 23:12:54.489444', '2027-02-28', '2026-03-01', NULL, 0, 36, 3);


--
-- Data for Name: ems_energy_cost_record; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_energy_cost_record (id, created_at, updated_at, consumption, cost, flat_consumption, peak_consumption, period_type, power_factor, record_date, remark, sharp_consumption, valley_consumption, energy_type_id, energy_unit_id) VALUES (1, '2026-02-09 23:36:56.239806', '2026-02-09 23:36:56.239806', 100.0000, 1.00, 30.0000, 30.0000, 'DAY', 0.20, '2026-02-09', NULL, 20.0000, 20.0000, 14, 36);
INSERT INTO public.ems_energy_cost_record (id, created_at, updated_at, consumption, cost, flat_consumption, peak_consumption, period_type, power_factor, record_date, remark, sharp_consumption, valley_consumption, energy_type_id, energy_unit_id) VALUES (2, '2026-02-09 23:37:35.156864', '2026-02-09 23:37:35.156864', 1000.0000, 1.00, 300.0000, 300.0000, 'MONTH', 0.80, '2026-01-09', NULL, 200.0000, 200.0000, 14, 36);


--
-- Data for Name: ems_energy_data; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (13, NULL, '2024-01-01 00:00:00', 'MONTH', 45680.2500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (14, NULL, '2024-02-01 00:00:00', 'MONTH', 42150.8000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (15, NULL, '2024-03-01 00:00:00', 'MONTH', 48320.1500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (16, NULL, '2024-04-01 00:00:00', 'MONTH', 51240.5000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (17, NULL, '2024-05-01 00:00:00', 'MONTH', 58760.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (18, NULL, '2024-06-01 00:00:00', 'MONTH', 72450.9000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (19, NULL, '2024-07-01 00:00:00', 'MONTH', 85320.4500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (20, NULL, '2024-08-01 00:00:00', 'MONTH', 82150.7000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (21, NULL, '2024-09-01 00:00:00', 'MONTH', 68450.2500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (22, NULL, '2024-10-01 00:00:00', 'MONTH', 54230.8000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (23, NULL, '2024-11-01 00:00:00', 'MONTH', 49870.1500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (24, NULL, '2024-12-01 00:00:00', 'MONTH', 52340.6000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (25, NULL, '2025-01-01 00:00:00', 'MONTH', 49334.6700, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (37, NULL, '2025-12-01 00:00:00', 'DAY', 1785.2500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (38, NULL, '2025-12-02 00:00:00', 'DAY', 1823.4000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (39, NULL, '2025-12-03 00:00:00', 'DAY', 1798.6500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (40, NULL, '2025-12-04 00:00:00', 'DAY', 1856.3000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (41, NULL, '2025-12-05 00:00:00', 'DAY', 1912.1500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (42, NULL, '2025-12-06 00:00:00', 'DAY', 1245.8000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (43, NULL, '2025-12-07 00:00:00', 'DAY', 1189.5500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (44, NULL, '2025-12-08 00:00:00', 'DAY', 1802.4500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (45, NULL, '2025-12-09 00:00:00', 'DAY', 1834.7000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (46, NULL, '2025-12-10 00:00:00', 'DAY', 1867.9500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (47, NULL, '2025-12-11 00:00:00', 'DAY', 1889.2000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (48, NULL, '2025-12-12 00:00:00', 'DAY', 1923.4500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (49, NULL, '2025-12-13 00:00:00', 'DAY', 1278.9000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (50, NULL, '2025-12-14 00:00:00', 'DAY', 1156.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (51, NULL, '2025-12-15 00:00:00', 'DAY', 1845.6000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (52, NULL, '2025-12-16 00:00:00', 'DAY', 1878.8500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (53, NULL, '2025-12-17 00:00:00', 'DAY', 1934.1000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (54, NULL, '2025-12-18 00:00:00', 'DAY', 1956.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (55, NULL, '2025-12-19 00:00:00', 'DAY', 1989.6000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (56, NULL, '2025-12-20 00:00:00', 'DAY', 1312.8500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (57, NULL, '2025-12-21 00:00:00', 'DAY', 1198.1000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (58, NULL, '2025-12-22 00:00:00', 'DAY', 1867.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (59, NULL, '2025-12-23 00:00:00', 'DAY', 1898.6000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (60, NULL, '2025-12-24 00:00:00', 'DAY', 1756.8500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (61, NULL, '2025-12-25 00:00:00', 'DAY', 1423.1000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (62, NULL, '2025-12-26 00:00:00', 'DAY', 1834.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (73, NULL, '2022-01-01 00:00:00', 'YEAR', 612450.5000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (74, NULL, '2023-01-01 00:00:00', 'YEAR', 658920.7500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (75, NULL, '2024-01-01 00:00:00', 'YEAR', 710962.9000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (76, NULL, '2025-01-01 00:00:00', 'YEAR', 767843.1700, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (77, NULL, '2024-01-01 00:00:00', 'MONTH', 1256.8000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (78, NULL, '2024-02-01 00:00:00', 'MONTH', 1178.4500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (79, NULL, '2024-03-01 00:00:00', 'MONTH', 1342.2000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (80, NULL, '2024-04-01 00:00:00', 'MONTH', 1456.9500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (81, NULL, '2024-05-01 00:00:00', 'MONTH', 1623.7000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (82, NULL, '2024-06-01 00:00:00', 'MONTH', 1845.4500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (83, NULL, '2024-07-01 00:00:00', 'MONTH', 2134.2000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (84, NULL, '2024-08-01 00:00:00', 'MONTH', 2078.9500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (85, NULL, '2024-09-01 00:00:00', 'MONTH', 1789.7000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (86, NULL, '2024-10-01 00:00:00', 'MONTH', 1534.4500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (87, NULL, '2024-11-01 00:00:00', 'MONTH', 1345.2000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (88, NULL, '2024-12-01 00:00:00', 'MONTH', 1289.9500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (89, NULL, '2025-01-01 00:00:00', 'MONTH', 1319.6400, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (102, NULL, '2024-01-01 00:00:00', 'MONTH', 45680.2500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (103, NULL, '2024-02-01 00:00:00', 'MONTH', 42150.8000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (104, NULL, '2024-03-01 00:00:00', 'MONTH', 48320.1500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (105, NULL, '2024-04-01 00:00:00', 'MONTH', 51240.5000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (106, NULL, '2024-05-01 00:00:00', 'MONTH', 58760.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (107, NULL, '2024-06-01 00:00:00', 'MONTH', 72450.9000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (108, NULL, '2024-07-01 00:00:00', 'MONTH', 85320.4500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (31, NULL, '2025-07-01 00:00:00', 'MONTH', 993.1962, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (30, NULL, '2025-06-01 00:00:00', 'MONTH', 960.0987, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (29, NULL, '2025-05-01 00:00:00', 'MONTH', 976.2449, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (28, NULL, '2025-04-01 00:00:00', 'MONTH', 972.2932, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (27, NULL, '2025-03-01 00:00:00', 'MONTH', 975.5159, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (26, NULL, '2025-02-01 00:00:00', 'MONTH', 975.0886, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (72, NULL, '2026-01-05 00:00:00', 'DAY', 39.5332, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (71, NULL, '2026-01-04 00:00:00', 'DAY', 34.2308, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (70, NULL, '2026-01-03 00:00:00', 'DAY', 40.4009, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (69, NULL, '2026-01-02 00:00:00', 'DAY', 36.2150, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (68, NULL, '2026-01-01 00:00:00', 'DAY', 35.5168, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (67, NULL, '2025-12-31 00:00:00', 'DAY', 39.1352, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (66, NULL, '2025-12-30 00:00:00', 'DAY', 39.4169, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (65, NULL, '2025-12-29 00:00:00', 'DAY', 35.4173, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (64, NULL, '2025-12-28 00:00:00', 'DAY', 37.8459, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (63, NULL, '2025-12-27 00:00:00', 'DAY', 40.6971, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (99, NULL, '2025-11-01 00:00:00', 'MONTH', 714.9088, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (34, NULL, '2025-10-01 00:00:00', 'MONTH', 965.1984, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (33, NULL, '2025-09-01 00:00:00', 'MONTH', 1016.1289, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (32, NULL, '2025-08-01 00:00:00', 'MONTH', 970.6966, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (98, NULL, '2025-10-01 00:00:00', 'MONTH', 780.5499, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (97, NULL, '2025-09-01 00:00:00', 'MONTH', 726.4559, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (96, NULL, '2025-08-01 00:00:00', 'MONTH', 724.0981, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (95, NULL, '2025-07-01 00:00:00', 'MONTH', 784.3847, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (94, NULL, '2025-06-01 00:00:00', 'MONTH', 764.9472, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (93, NULL, '2025-05-01 00:00:00', 'MONTH', 775.2540, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (92, NULL, '2025-04-01 00:00:00', 'MONTH', 732.1573, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (91, NULL, '2025-03-01 00:00:00', 'MONTH', 811.2812, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (90, NULL, '2025-02-01 00:00:00', 'MONTH', 786.7234, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (35, NULL, '2025-11-01 00:00:00', 'MONTH', 921.8087, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (100, NULL, '2025-12-01 00:00:00', 'MONTH', 737.7613, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (109, NULL, '2024-08-01 00:00:00', 'MONTH', 82150.7000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (110, NULL, '2024-09-01 00:00:00', 'MONTH', 68450.2500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (111, NULL, '2024-10-01 00:00:00', 'MONTH', 54230.8000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (112, NULL, '2024-11-01 00:00:00', 'MONTH', 49870.1500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (113, NULL, '2024-12-01 00:00:00', 'MONTH', 52340.6000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (114, NULL, '2025-01-01 00:00:00', 'MONTH', 49334.6700, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (115, NULL, '2025-02-01 00:00:00', 'MONTH', 45522.8600, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (116, NULL, '2025-03-01 00:00:00', 'MONTH', 52185.7600, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (117, NULL, '2025-04-01 00:00:00', 'MONTH', 55339.7400, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (118, NULL, '2025-05-01 00:00:00', 'MONTH', 63461.1800, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (119, NULL, '2025-06-01 00:00:00', 'MONTH', 78246.9700, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (120, NULL, '2025-07-01 00:00:00', 'MONTH', 92146.0900, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (121, NULL, '2025-08-01 00:00:00', 'MONTH', 88722.7600, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (122, NULL, '2025-09-01 00:00:00', 'MONTH', 73926.2700, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (123, NULL, '2025-10-01 00:00:00', 'MONTH', 58569.2600, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (124, NULL, '2025-11-01 00:00:00', 'MONTH', 53859.7600, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (126, NULL, '2025-12-01 00:00:00', 'DAY', 1785.2500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (127, NULL, '2025-12-02 00:00:00', 'DAY', 1823.4000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (128, NULL, '2025-12-03 00:00:00', 'DAY', 1798.6500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (129, NULL, '2025-12-04 00:00:00', 'DAY', 1856.3000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (130, NULL, '2025-12-05 00:00:00', 'DAY', 1912.1500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (131, NULL, '2025-12-06 00:00:00', 'DAY', 1245.8000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (132, NULL, '2025-12-07 00:00:00', 'DAY', 1189.5500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (133, NULL, '2025-12-08 00:00:00', 'DAY', 1802.4500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (134, NULL, '2025-12-09 00:00:00', 'DAY', 1834.7000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (135, NULL, '2025-12-10 00:00:00', 'DAY', 1867.9500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (136, NULL, '2025-12-11 00:00:00', 'DAY', 1889.2000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (137, NULL, '2025-12-12 00:00:00', 'DAY', 1923.4500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (138, NULL, '2025-12-13 00:00:00', 'DAY', 1278.9000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (139, NULL, '2025-12-14 00:00:00', 'DAY', 1156.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (140, NULL, '2025-12-15 00:00:00', 'DAY', 1845.6000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (141, NULL, '2025-12-16 00:00:00', 'DAY', 1878.8500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (142, NULL, '2025-12-17 00:00:00', 'DAY', 1934.1000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (143, NULL, '2025-12-18 00:00:00', 'DAY', 1956.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (144, NULL, '2025-12-19 00:00:00', 'DAY', 1989.6000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (145, NULL, '2025-12-20 00:00:00', 'DAY', 1312.8500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (146, NULL, '2025-12-21 00:00:00', 'DAY', 1198.1000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (147, NULL, '2025-12-22 00:00:00', 'DAY', 1867.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (148, NULL, '2025-12-23 00:00:00', 'DAY', 1898.6000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (149, NULL, '2025-12-24 00:00:00', 'DAY', 1756.8500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (150, NULL, '2025-12-25 00:00:00', 'DAY', 1423.1000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (151, NULL, '2025-12-26 00:00:00', 'DAY', 1834.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (152, NULL, '2025-12-27 00:00:00', 'DAY', 1289.6000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (153, NULL, '2025-12-28 00:00:00', 'DAY', 1178.8500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (154, NULL, '2025-12-29 00:00:00', 'DAY', 1812.1000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (155, NULL, '2025-12-30 00:00:00', 'DAY', 1845.3500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (156, NULL, '2025-12-31 00:00:00', 'DAY', 1667.8000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (157, NULL, '2026-01-01 00:00:00', 'DAY', 1456.2000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (158, NULL, '2026-01-02 00:00:00', 'DAY', 1789.4500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (159, NULL, '2026-01-03 00:00:00', 'DAY', 1834.7000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (160, NULL, '2026-01-04 00:00:00', 'DAY', 1267.9500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (161, NULL, '2026-01-05 00:00:00', 'DAY', 1198.2000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (162, NULL, '2022-01-01 00:00:00', 'YEAR', 612450.5000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (163, NULL, '2023-01-01 00:00:00', 'YEAR', 658920.7500, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (164, NULL, '2024-01-01 00:00:00', 'YEAR', 710962.9000, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (165, NULL, '2025-01-01 00:00:00', 'YEAR', 767843.1700, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (166, NULL, '2024-01-01 00:00:00', 'MONTH', 1256.8000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (167, NULL, '2024-02-01 00:00:00', 'MONTH', 1178.4500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (168, NULL, '2024-03-01 00:00:00', 'MONTH', 1342.2000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (169, NULL, '2024-04-01 00:00:00', 'MONTH', 1456.9500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (170, NULL, '2024-05-01 00:00:00', 'MONTH', 1623.7000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (171, NULL, '2024-06-01 00:00:00', 'MONTH', 1845.4500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (172, NULL, '2024-07-01 00:00:00', 'MONTH', 2134.2000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (173, NULL, '2024-08-01 00:00:00', 'MONTH', 2078.9500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (174, NULL, '2024-09-01 00:00:00', 'MONTH', 1789.7000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (175, NULL, '2024-10-01 00:00:00', 'MONTH', 1534.4500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (176, NULL, '2024-11-01 00:00:00', 'MONTH', 1345.2000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (177, NULL, '2024-12-01 00:00:00', 'MONTH', 1289.9500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (178, NULL, '2025-01-01 00:00:00', 'MONTH', 1319.6400, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (179, NULL, '2025-02-01 00:00:00', 'MONTH', 1237.3700, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (180, NULL, '2025-03-01 00:00:00', 'MONTH', 1409.3100, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (181, NULL, '2025-04-01 00:00:00', 'MONTH', 1529.8000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (182, NULL, '2025-05-01 00:00:00', 'MONTH', 1704.8900, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (183, NULL, '2025-06-01 00:00:00', 'MONTH', 1937.7200, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (184, NULL, '2025-07-01 00:00:00', 'MONTH', 2240.9100, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (185, NULL, '2025-08-01 00:00:00', 'MONTH', 2182.9000, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (186, NULL, '2025-09-01 00:00:00', 'MONTH', 1879.1900, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (187, NULL, '2025-10-01 00:00:00', 'MONTH', 1611.1700, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (188, NULL, '2025-11-01 00:00:00', 'MONTH', 1412.4600, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (189, NULL, '2025-12-01 00:00:00', 'MONTH', 1354.4500, 9, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (190, NULL, '2024-01-01 00:00:00', 'MONTH', 8956.3000, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (191, NULL, '2024-02-01 00:00:00', 'MONTH', 8234.8500, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (192, NULL, '2024-03-01 00:00:00', 'MONTH', 5678.4000, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (193, NULL, '2024-04-01 00:00:00', 'MONTH', 2345.9500, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (194, NULL, '2024-05-01 00:00:00', 'MONTH', 1234.5000, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (195, NULL, '2024-06-01 00:00:00', 'MONTH', 856.0500, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (196, NULL, '2024-07-01 00:00:00', 'MONTH', 678.6000, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (197, NULL, '2024-08-01 00:00:00', 'MONTH', 712.1500, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (198, NULL, '2024-09-01 00:00:00', 'MONTH', 1456.7000, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (199, NULL, '2024-10-01 00:00:00', 'MONTH', 3567.2500, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (200, NULL, '2024-11-01 00:00:00', 'MONTH', 6789.8000, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (201, NULL, '2024-12-01 00:00:00', 'MONTH', 8123.3500, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (202, NULL, '2025-01-01 00:00:00', 'MONTH', 9404.1200, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (203, NULL, '2025-02-01 00:00:00', 'MONTH', 8646.5900, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (204, NULL, '2025-03-01 00:00:00', 'MONTH', 5962.3200, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (205, NULL, '2025-04-01 00:00:00', 'MONTH', 2463.2500, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (206, NULL, '2025-05-01 00:00:00', 'MONTH', 1296.2300, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (207, NULL, '2025-06-01 00:00:00', 'MONTH', 898.8500, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (208, NULL, '2025-07-01 00:00:00', 'MONTH', 712.5300, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (209, NULL, '2025-08-01 00:00:00', 'MONTH', 747.7600, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (210, NULL, '2025-09-01 00:00:00', 'MONTH', 1529.5400, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (211, NULL, '2025-10-01 00:00:00', 'MONTH', 3745.6100, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (212, NULL, '2025-11-01 00:00:00', 'MONTH', 7129.2900, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (213, NULL, '2025-12-01 00:00:00', 'MONTH', 8529.5200, 3, 14);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (214, NULL, '2024-01-01 00:00:00', 'MONTH', 2456.3000, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (215, NULL, '2024-02-01 00:00:00', 'MONTH', 2234.8500, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (216, NULL, '2024-03-01 00:00:00', 'MONTH', 2078.4000, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (217, NULL, '2024-04-01 00:00:00', 'MONTH', 1845.9500, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (218, NULL, '2024-05-01 00:00:00', 'MONTH', 1634.5000, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (219, NULL, '2024-06-01 00:00:00', 'MONTH', 1456.0500, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (220, NULL, '2024-07-01 00:00:00', 'MONTH', 1278.6000, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (221, NULL, '2024-08-01 00:00:00', 'MONTH', 1312.1500, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (222, NULL, '2024-09-01 00:00:00', 'MONTH', 1556.7000, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (223, NULL, '2024-10-01 00:00:00', 'MONTH', 1867.2500, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (224, NULL, '2024-11-01 00:00:00', 'MONTH', 2189.8000, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (225, NULL, '2024-12-01 00:00:00', 'MONTH', 2423.3500, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (226, NULL, '2025-01-01 00:00:00', 'MONTH', 2579.1200, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (240, '2026-01-25 11:53:45.237818', '2026-01-24 00:00:00', 'DAY', 40.4699, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (125, NULL, '2025-12-01 00:00:00', 'MONTH', 1385.1116, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (242, '2026-01-25 11:53:45.25093', '2026-01-22 00:00:00', 'DAY', 35.1819, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (243, '2026-01-25 11:53:45.258317', '2026-01-21 00:00:00', 'DAY', 33.2091, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (244, '2026-01-25 11:53:45.264072', '2026-01-20 00:00:00', 'DAY', 36.8066, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (245, '2026-01-25 11:53:45.275712', '2026-01-19 00:00:00', 'DAY', 35.0164, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (246, '2026-01-25 11:53:45.281578', '2026-01-18 00:00:00', 'DAY', 35.1495, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (247, '2026-01-25 11:53:45.291432', '2026-01-17 00:00:00', 'DAY', 33.6138, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (248, '2026-01-25 11:53:45.296166', '2026-01-16 00:00:00', 'DAY', 38.7359, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (249, '2026-01-25 11:53:45.314949', '2026-01-15 00:00:00', 'DAY', 31.5590, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (250, '2026-01-25 11:53:45.319956', '2026-01-14 00:00:00', 'DAY', 32.1578, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (251, '2026-01-25 11:53:45.32608', '2026-01-13 00:00:00', 'DAY', 31.0645, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (252, '2026-01-25 11:53:45.329753', '2026-01-12 00:00:00', 'DAY', 31.7201, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (253, '2026-01-25 11:53:45.335992', '2026-01-11 00:00:00', 'DAY', 34.3777, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (254, '2026-01-25 11:53:45.341296', '2026-01-10 00:00:00', 'DAY', 40.5188, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (255, '2026-01-25 11:53:45.345535', '2026-01-09 00:00:00', 'DAY', 37.6426, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (256, '2026-01-25 11:53:45.34952', '2026-01-08 00:00:00', 'DAY', 37.6966, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (257, '2026-01-25 11:53:45.353985', '2026-01-07 00:00:00', 'DAY', 39.2263, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (258, '2026-01-25 11:53:45.359219', '2026-01-06 00:00:00', 'DAY', 35.4066, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (259, '2026-01-25 11:53:45.4116', '2026-01-01 00:00:00', 'YEAR', 11080.4626, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (36, NULL, '2025-12-01 00:00:00', 'MONTH', 984.6715, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (239, '2026-01-25 11:53:45.231338', '2026-01-25 00:00:00', 'DAY', 39.0042, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (260, '2026-01-25 11:53:45.583181', '2026-01-01 00:00:00', 'MONTH', 1297.7682, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (261, '2026-01-25 11:53:45.586834', '2025-12-01 00:00:00', 'MONTH', 1211.6934, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (262, '2026-01-25 11:53:45.590623', '2025-11-01 00:00:00', 'MONTH', 1261.0566, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (263, '2026-01-25 11:53:45.593802', '2025-10-01 00:00:00', 'MONTH', 1242.4401, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (264, '2026-01-25 11:53:45.59696', '2025-09-01 00:00:00', 'MONTH', 1301.4214, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (265, '2026-01-25 11:53:45.600578', '2025-08-01 00:00:00', 'MONTH', 1247.5205, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (266, '2026-01-25 11:53:45.603933', '2025-07-01 00:00:00', 'MONTH', 1208.7617, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (267, '2026-01-25 11:53:45.607127', '2025-06-01 00:00:00', 'MONTH', 1224.6288, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (268, '2026-01-25 11:53:45.609937', '2025-05-01 00:00:00', 'MONTH', 1227.8162, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (269, '2026-01-25 11:53:45.613223', '2025-04-01 00:00:00', 'MONTH', 1249.2799, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (270, '2026-01-25 11:53:45.615909', '2025-03-01 00:00:00', 'MONTH', 1279.2247, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (271, '2026-01-25 11:53:45.618685', '2025-02-01 00:00:00', 'MONTH', 1280.7341, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (272, '2026-01-25 11:53:45.621021', '2026-01-25 00:00:00', 'DAY', 42.9140, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (273, '2026-01-25 11:53:45.623771', '2026-01-24 00:00:00', 'DAY', 48.1492, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (274, '2026-01-25 11:53:45.626585', '2026-01-23 00:00:00', 'DAY', 44.5635, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (275, '2026-01-25 11:53:45.629839', '2026-01-22 00:00:00', 'DAY', 44.8659, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (276, '2026-01-25 11:53:45.632764', '2026-01-21 00:00:00', 'DAY', 40.7565, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (277, '2026-01-25 11:53:45.643142', '2026-01-20 00:00:00', 'DAY', 42.3484, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (278, '2026-01-25 11:53:45.646444', '2026-01-19 00:00:00', 'DAY', 48.1314, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (279, '2026-01-25 11:53:45.650176', '2026-01-18 00:00:00', 'DAY', 47.1695, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (280, '2026-01-25 11:53:45.654137', '2026-01-17 00:00:00', 'DAY', 45.6708, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (281, '2026-01-25 11:53:45.656926', '2026-01-16 00:00:00', 'DAY', 47.8959, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (282, '2026-01-25 11:53:45.659845', '2026-01-15 00:00:00', 'DAY', 41.8486, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (283, '2026-01-25 11:53:45.662543', '2026-01-14 00:00:00', 'DAY', 44.1776, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (284, '2026-01-25 11:53:45.665539', '2026-01-13 00:00:00', 'DAY', 44.7093, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (285, '2026-01-25 11:53:45.668745', '2026-01-12 00:00:00', 'DAY', 46.5693, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (286, '2026-01-25 11:53:45.67148', '2026-01-11 00:00:00', 'DAY', 48.3167, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (287, '2026-01-25 11:53:45.675295', '2026-01-10 00:00:00', 'DAY', 50.1677, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (288, '2026-01-25 11:53:45.67826', '2026-01-09 00:00:00', 'DAY', 43.6649, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (289, '2026-01-25 11:53:45.681183', '2026-01-08 00:00:00', 'DAY', 45.3734, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (290, '2026-01-25 11:53:45.683664', '2026-01-07 00:00:00', 'DAY', 40.6576, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (291, '2026-01-25 11:53:45.68641', '2026-01-06 00:00:00', 'DAY', 43.4904, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (292, '2026-01-25 11:53:45.688993', '2026-01-05 00:00:00', 'DAY', 46.2987, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (293, '2026-01-25 11:53:45.691862', '2026-01-04 00:00:00', 'DAY', 43.4454, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (294, '2026-01-25 11:53:45.694879', '2026-01-03 00:00:00', 'DAY', 44.7031, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (295, '2026-01-25 11:53:45.697594', '2026-01-02 00:00:00', 'DAY', 45.6367, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (296, '2026-01-25 11:53:45.700701', '2026-01-01 00:00:00', 'DAY', 47.1501, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (297, '2026-01-25 11:53:45.704208', '2025-12-31 00:00:00', 'DAY', 46.3394, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (298, '2026-01-25 11:53:45.708325', '2025-12-30 00:00:00', 'DAY', 46.1369, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (299, '2026-01-25 11:53:45.712457', '2025-12-29 00:00:00', 'DAY', 49.7780, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (300, '2026-01-25 11:53:45.716995', '2025-12-28 00:00:00', 'DAY', 40.5274, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (301, '2026-01-25 11:53:45.72074', '2025-12-27 00:00:00', 'DAY', 48.0032, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (302, '2026-01-25 11:53:45.725732', '2026-01-01 00:00:00', 'YEAR', 14939.3684, 14, 26);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (303, '2026-01-25 11:53:45.729645', '2026-01-01 00:00:00', 'MONTH', 1193.9762, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (237, NULL, '2025-12-01 00:00:00', 'MONTH', 1132.1648, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (236, NULL, '2025-11-01 00:00:00', 'MONTH', 1176.2698, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (235, NULL, '2025-10-01 00:00:00', 'MONTH', 1167.5407, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (234, NULL, '2025-09-01 00:00:00', 'MONTH', 1107.2883, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (233, NULL, '2025-08-01 00:00:00', 'MONTH', 1199.1162, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (232, NULL, '2025-07-01 00:00:00', 'MONTH', 1195.5861, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (231, NULL, '2025-06-01 00:00:00', 'MONTH', 1133.7111, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (230, NULL, '2025-05-01 00:00:00', 'MONTH', 1189.8014, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (229, NULL, '2025-04-01 00:00:00', 'MONTH', 1122.7786, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (228, NULL, '2025-03-01 00:00:00', 'MONTH', 1191.7082, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (227, NULL, '2025-02-01 00:00:00', 'MONTH', 1107.2273, 7, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (304, '2026-01-25 11:53:45.755912', '2026-01-25 00:00:00', 'DAY', 38.4641, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (305, '2026-01-25 11:53:45.758212', '2026-01-24 00:00:00', 'DAY', 43.4016, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (306, '2026-01-25 11:53:45.76057', '2026-01-23 00:00:00', 'DAY', 43.7338, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (307, '2026-01-25 11:53:45.762708', '2026-01-22 00:00:00', 'DAY', 46.0836, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (308, '2026-01-25 11:53:45.764775', '2026-01-21 00:00:00', 'DAY', 38.4050, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (309, '2026-01-25 11:53:45.766835', '2026-01-20 00:00:00', 'DAY', 40.6247, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (310, '2026-01-25 11:53:45.768855', '2026-01-19 00:00:00', 'DAY', 43.7643, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (241, '2026-01-25 11:53:45.243008', '2026-01-23 00:00:00', 'DAY', 34.8712, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (311, '2026-01-25 11:53:45.770881', '2026-01-18 00:00:00', 'DAY', 37.4421, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (312, '2026-01-25 11:53:45.773559', '2026-01-17 00:00:00', 'DAY', 42.3659, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (313, '2026-01-25 11:53:45.775668', '2026-01-16 00:00:00', 'DAY', 38.4794, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (314, '2026-01-25 11:53:45.777667', '2026-01-15 00:00:00', 'DAY', 40.0290, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (315, '2026-01-25 11:53:45.779765', '2026-01-14 00:00:00', 'DAY', 38.8470, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (316, '2026-01-25 11:53:45.781794', '2026-01-13 00:00:00', 'DAY', 39.4890, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (317, '2026-01-25 11:53:45.783753', '2026-01-12 00:00:00', 'DAY', 46.1596, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (318, '2026-01-25 11:53:45.785761', '2026-01-11 00:00:00', 'DAY', 39.8656, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (319, '2026-01-25 11:53:45.787746', '2026-01-10 00:00:00', 'DAY', 46.3298, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (320, '2026-01-25 11:53:45.789665', '2026-01-09 00:00:00', 'DAY', 45.4655, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (321, '2026-01-25 11:53:45.791648', '2026-01-08 00:00:00', 'DAY', 45.7851, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (322, '2026-01-25 11:53:45.793669', '2026-01-07 00:00:00', 'DAY', 41.2680, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (323, '2026-01-25 11:53:45.795793', '2026-01-06 00:00:00', 'DAY', 39.0443, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (324, '2026-01-25 11:53:45.797734', '2026-01-05 00:00:00', 'DAY', 41.5795, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (325, '2026-01-25 11:53:45.799952', '2026-01-04 00:00:00', 'DAY', 39.4752, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (326, '2026-01-25 11:53:45.801936', '2026-01-03 00:00:00', 'DAY', 44.3942, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (327, '2026-01-25 11:53:45.80391', '2026-01-02 00:00:00', 'DAY', 44.0532, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (328, '2026-01-25 11:53:45.806237', '2026-01-01 00:00:00', 'DAY', 41.8653, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (329, '2026-01-25 11:53:45.80867', '2025-12-31 00:00:00', 'DAY', 38.7698, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (330, '2026-01-25 11:53:45.810803', '2025-12-30 00:00:00', 'DAY', 37.6326, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (331, '2026-01-25 11:53:45.813006', '2025-12-29 00:00:00', 'DAY', 45.7469, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (332, '2026-01-25 11:53:45.815375', '2025-12-28 00:00:00', 'DAY', 43.5442, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (333, '2026-01-25 11:53:45.817935', '2025-12-27 00:00:00', 'DAY', 41.3892, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (334, '2026-01-25 11:53:45.820291', '2026-01-01 00:00:00', 'YEAR', 13482.3599, 14, 18);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (335, '2026-01-25 11:53:46.003107', '2026-01-01 00:00:00', 'MONTH', 755.5165, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (336, '2026-01-25 11:53:46.026483', '2026-01-25 00:00:00', 'DAY', 23.8650, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (337, '2026-01-25 11:53:46.02949', '2026-01-24 00:00:00', 'DAY', 29.7520, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (338, '2026-01-25 11:53:46.031671', '2026-01-23 00:00:00', 'DAY', 27.1292, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (339, '2026-01-25 11:53:46.033991', '2026-01-22 00:00:00', 'DAY', 27.7954, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (340, '2026-01-25 11:53:46.036841', '2026-01-21 00:00:00', 'DAY', 32.7577, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (341, '2026-01-25 11:53:46.039146', '2026-01-20 00:00:00', 'DAY', 31.4606, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (342, '2026-01-25 11:53:46.041213', '2026-01-19 00:00:00', 'DAY', 25.4570, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (343, '2026-01-25 11:53:46.044276', '2026-01-18 00:00:00', 'DAY', 28.0988, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (344, '2026-01-25 11:53:46.046687', '2026-01-17 00:00:00', 'DAY', 31.4490, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (345, '2026-01-25 11:53:46.049033', '2026-01-16 00:00:00', 'DAY', 24.6761, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (346, '2026-01-25 11:53:46.051316', '2026-01-15 00:00:00', 'DAY', 28.3847, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (347, '2026-01-25 11:53:46.05373', '2026-01-14 00:00:00', 'DAY', 31.9465, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (348, '2026-01-25 11:53:46.055865', '2026-01-13 00:00:00', 'DAY', 27.7874, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (349, '2026-01-25 11:53:46.058068', '2026-01-12 00:00:00', 'DAY', 25.5417, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (350, '2026-01-25 11:53:46.060763', '2026-01-11 00:00:00', 'DAY', 28.9566, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (351, '2026-01-25 11:53:46.063431', '2026-01-10 00:00:00', 'DAY', 25.3294, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (352, '2026-01-25 11:53:46.066097', '2026-01-09 00:00:00', 'DAY', 30.2717, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (353, '2026-01-25 11:53:46.068512', '2026-01-08 00:00:00', 'DAY', 26.4906, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (354, '2026-01-25 11:53:46.070905', '2026-01-07 00:00:00', 'DAY', 28.9901, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (355, '2026-01-25 11:53:46.073188', '2026-01-06 00:00:00', 'DAY', 31.1752, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (356, '2026-01-25 11:53:46.075846', '2026-01-05 00:00:00', 'DAY', 33.5139, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (357, '2026-01-25 11:53:46.078303', '2026-01-04 00:00:00', 'DAY', 29.2193, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (358, '2026-01-25 11:53:46.08073', '2026-01-03 00:00:00', 'DAY', 33.0711, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (359, '2026-01-25 11:53:46.082965', '2026-01-02 00:00:00', 'DAY', 33.1898, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (360, '2026-01-25 11:53:46.085473', '2026-01-01 00:00:00', 'DAY', 32.4590, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (361, '2026-01-25 11:53:46.087647', '2025-12-31 00:00:00', 'DAY', 24.4312, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (362, '2026-01-25 11:53:46.08989', '2025-12-30 00:00:00', 'DAY', 33.3560, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (363, '2026-01-25 11:53:46.092312', '2025-12-29 00:00:00', 'DAY', 27.3254, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (364, '2026-01-25 11:53:46.095116', '2025-12-28 00:00:00', 'DAY', 26.4246, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (365, '2026-01-25 11:53:46.098104', '2025-12-27 00:00:00', 'DAY', 32.6765, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (366, '2026-01-25 11:53:46.100594', '2026-01-01 00:00:00', 'YEAR', 8789.8680, 14, 12);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (367, '2026-01-25 11:53:46.187083', '2026-01-01 00:00:00', 'MONTH', 646.8423, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (368, '2026-01-25 11:53:46.189087', '2025-12-01 00:00:00', 'MONTH', 701.0516, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (369, '2026-01-25 11:53:46.195558', '2025-11-01 00:00:00', 'MONTH', 664.2587, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (370, '2026-01-25 11:53:46.202281', '2025-10-01 00:00:00', 'MONTH', 611.7551, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (371, '2026-01-25 11:53:46.207582', '2025-09-01 00:00:00', 'MONTH', 689.0508, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (372, '2026-01-25 11:53:46.211297', '2025-08-01 00:00:00', 'MONTH', 678.6363, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (373, '2026-01-25 11:53:46.214514', '2025-07-01 00:00:00', 'MONTH', 651.7940, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (374, '2026-01-25 11:53:46.216619', '2025-06-01 00:00:00', 'MONTH', 637.1104, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (375, '2026-01-25 11:53:46.218872', '2025-05-01 00:00:00', 'MONTH', 643.6728, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (376, '2026-01-25 11:53:46.220886', '2025-04-01 00:00:00', 'MONTH', 643.1197, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (377, '2026-01-25 11:53:46.223073', '2025-03-01 00:00:00', 'MONTH', 681.9266, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (378, '2026-01-25 11:53:46.225394', '2025-02-01 00:00:00', 'MONTH', 651.6576, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (379, '2026-01-25 11:53:46.228009', '2026-01-25 00:00:00', 'DAY', 24.5436, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (380, '2026-01-25 11:53:46.23047', '2026-01-24 00:00:00', 'DAY', 28.2155, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (381, '2026-01-25 11:53:46.232956', '2026-01-23 00:00:00', 'DAY', 24.1095, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (382, '2026-01-25 11:53:46.239022', '2026-01-22 00:00:00', 'DAY', 25.1308, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (383, '2026-01-25 11:53:46.241349', '2026-01-21 00:00:00', 'DAY', 28.5382, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (384, '2026-01-25 11:53:46.243696', '2026-01-20 00:00:00', 'DAY', 24.8155, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (385, '2026-01-25 11:53:46.247212', '2026-01-19 00:00:00', 'DAY', 21.7100, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (386, '2026-01-25 11:53:46.249571', '2026-01-18 00:00:00', 'DAY', 29.2350, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (387, '2026-01-25 11:53:46.251942', '2026-01-17 00:00:00', 'DAY', 29.1420, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (388, '2026-01-25 11:53:46.254136', '2026-01-16 00:00:00', 'DAY', 26.2688, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (389, '2026-01-25 11:53:46.256251', '2026-01-15 00:00:00', 'DAY', 29.6687, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (390, '2026-01-25 11:53:46.258663', '2026-01-14 00:00:00', 'DAY', 28.9437, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (391, '2026-01-25 11:53:46.260897', '2026-01-13 00:00:00', 'DAY', 26.1453, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (392, '2026-01-25 11:53:46.263421', '2026-01-12 00:00:00', 'DAY', 28.6587, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (393, '2026-01-25 11:53:46.266051', '2026-01-11 00:00:00', 'DAY', 23.5029, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (394, '2026-01-25 11:53:46.269324', '2026-01-10 00:00:00', 'DAY', 26.4832, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (395, '2026-01-25 11:53:46.271631', '2026-01-09 00:00:00', 'DAY', 22.3620, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (396, '2026-01-25 11:53:46.274204', '2026-01-08 00:00:00', 'DAY', 26.9190, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (397, '2026-01-25 11:53:46.276689', '2026-01-07 00:00:00', 'DAY', 25.6648, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (398, '2026-01-25 11:53:46.279265', '2026-01-06 00:00:00', 'DAY', 21.3356, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (399, '2026-01-25 11:53:46.281644', '2026-01-05 00:00:00', 'DAY', 28.6866, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (400, '2026-01-25 11:53:46.283996', '2026-01-04 00:00:00', 'DAY', 26.7128, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (401, '2026-01-25 11:53:46.28655', '2026-01-03 00:00:00', 'DAY', 29.3817, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (402, '2026-01-25 11:53:46.289816', '2026-01-02 00:00:00', 'DAY', 29.4773, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (403, '2026-01-25 11:53:46.293461', '2026-01-01 00:00:00', 'DAY', 24.6366, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (404, '2026-01-25 11:53:46.296987', '2025-12-31 00:00:00', 'DAY', 23.7599, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (405, '2026-01-25 11:53:46.300519', '2025-12-30 00:00:00', 'DAY', 27.7605, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (406, '2026-01-25 11:53:46.303982', '2025-12-29 00:00:00', 'DAY', 30.0242, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (407, '2026-01-25 11:53:46.307737', '2025-12-28 00:00:00', 'DAY', 25.8240, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (408, '2026-01-25 11:53:46.310337', '2025-12-27 00:00:00', 'DAY', 29.9337, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (409, '2026-01-25 11:53:46.312883', '2026-01-01 00:00:00', 'YEAR', 7237.7060, 14, 27);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (238, '2026-01-25 11:53:45.04652', '2026-01-01 00:00:00', 'MONTH', 942.0385, 14, 1);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (410, '2026-01-25 11:55:28.91173', '2026-01-01 00:00:00', 'MONTH', 921.5647, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (411, '2026-01-25 11:55:28.914959', '2025-12-01 00:00:00', 'MONTH', 861.2445, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (412, '2026-01-25 11:55:28.917978', '2025-11-01 00:00:00', 'MONTH', 919.0966, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (413, '2026-01-25 11:55:28.920461', '2025-10-01 00:00:00', 'MONTH', 893.0653, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (414, '2026-01-25 11:55:28.922685', '2025-09-01 00:00:00', 'MONTH', 857.9892, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (415, '2026-01-25 11:55:28.924501', '2025-08-01 00:00:00', 'MONTH', 886.3281, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (416, '2026-01-25 11:55:28.926261', '2025-07-01 00:00:00', 'MONTH', 854.6902, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (417, '2026-01-25 11:55:28.928167', '2025-06-01 00:00:00', 'MONTH', 862.8440, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (418, '2026-01-25 11:55:28.929965', '2025-05-01 00:00:00', 'MONTH', 925.2806, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (419, '2026-01-25 11:55:28.931723', '2025-04-01 00:00:00', 'MONTH', 897.2749, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (420, '2026-01-25 11:55:28.933448', '2025-03-01 00:00:00', 'MONTH', 838.3578, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (421, '2026-01-25 11:55:28.935237', '2025-02-01 00:00:00', 'MONTH', 881.7041, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (422, '2026-01-25 11:55:28.936974', '2026-01-25 00:00:00', 'DAY', 30.4263, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (423, '2026-01-25 11:55:28.938743', '2026-01-24 00:00:00', 'DAY', 32.2497, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (424, '2026-01-25 11:55:28.94046', '2026-01-23 00:00:00', 'DAY', 28.1068, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (425, '2026-01-25 11:55:28.942221', '2026-01-22 00:00:00', 'DAY', 35.7195, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (426, '2026-01-25 11:55:28.944033', '2026-01-21 00:00:00', 'DAY', 31.1571, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (427, '2026-01-25 11:55:28.946151', '2026-01-20 00:00:00', 'DAY', 33.5558, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (428, '2026-01-25 11:55:28.948122', '2026-01-19 00:00:00', 'DAY', 28.1807, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (429, '2026-01-25 11:55:28.950542', '2026-01-18 00:00:00', 'DAY', 36.7942, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (430, '2026-01-25 11:55:28.952312', '2026-01-17 00:00:00', 'DAY', 30.7597, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (431, '2026-01-25 11:55:28.954109', '2026-01-16 00:00:00', 'DAY', 35.3898, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (432, '2026-01-25 11:55:28.956205', '2026-01-15 00:00:00', 'DAY', 31.0333, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (433, '2026-01-25 11:55:28.958203', '2026-01-14 00:00:00', 'DAY', 32.6652, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (434, '2026-01-25 11:55:28.960326', '2026-01-13 00:00:00', 'DAY', 29.4412, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (435, '2026-01-25 11:55:28.962378', '2026-01-12 00:00:00', 'DAY', 31.8347, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (436, '2026-01-25 11:55:28.965678', '2026-01-11 00:00:00', 'DAY', 27.6347, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (437, '2026-01-25 11:55:28.970115', '2026-01-10 00:00:00', 'DAY', 34.4969, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (438, '2026-01-25 11:55:28.976993', '2026-01-09 00:00:00', 'DAY', 36.2737, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (439, '2026-01-25 11:55:28.983655', '2026-01-08 00:00:00', 'DAY', 35.2561, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (440, '2026-01-25 11:55:28.990026', '2026-01-07 00:00:00', 'DAY', 35.3391, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (441, '2026-01-25 11:55:28.998101', '2026-01-06 00:00:00', 'DAY', 29.3306, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (442, '2026-01-25 11:55:29.006836', '2026-01-05 00:00:00', 'DAY', 33.5587, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (443, '2026-01-25 11:55:29.018589', '2026-01-04 00:00:00', 'DAY', 31.5847, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (444, '2026-01-25 11:55:29.028411', '2026-01-03 00:00:00', 'DAY', 34.7125, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (445, '2026-01-25 11:55:29.039054', '2026-01-02 00:00:00', 'DAY', 30.8439, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (446, '2026-01-25 11:55:29.047692', '2026-01-01 00:00:00', 'DAY', 28.8795, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (447, '2026-01-25 11:55:29.054126', '2025-12-31 00:00:00', 'DAY', 34.6206, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (448, '2026-01-25 11:55:29.063755', '2025-12-30 00:00:00', 'DAY', 31.3056, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (449, '2026-01-25 11:55:29.071443', '2025-12-29 00:00:00', 'DAY', 28.0305, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (450, '2026-01-25 11:55:29.080693', '2025-12-28 00:00:00', 'DAY', 36.5161, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (451, '2026-01-25 11:55:29.090425', '2025-12-27 00:00:00', 'DAY', 30.2759, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (452, '2026-01-25 11:55:29.101107', '2026-01-01 00:00:00', 'YEAR', 10041.3567, 14, 28);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (453, '2026-01-25 11:55:29.121911', '2026-01-01 00:00:00', 'MONTH', 814.2419, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (454, '2026-01-25 11:55:29.127425', '2025-12-01 00:00:00', 'MONTH', 732.7494, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (455, '2026-01-25 11:55:29.133738', '2025-11-01 00:00:00', 'MONTH', 778.6074, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (456, '2026-01-25 11:55:29.139753', '2025-10-01 00:00:00', 'MONTH', 719.0607, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (457, '2026-01-25 11:55:29.148197', '2025-09-01 00:00:00', 'MONTH', 777.3402, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (458, '2026-01-25 11:55:29.154309', '2025-08-01 00:00:00', 'MONTH', 801.6500, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (459, '2026-01-25 11:55:29.158717', '2025-07-01 00:00:00', 'MONTH', 787.3232, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (460, '2026-01-25 11:55:29.162865', '2025-06-01 00:00:00', 'MONTH', 730.2168, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (461, '2026-01-25 11:55:29.166986', '2025-05-01 00:00:00', 'MONTH', 729.7438, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (462, '2026-01-25 11:55:29.172952', '2025-04-01 00:00:00', 'MONTH', 768.1858, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (463, '2026-01-25 11:55:29.177634', '2025-03-01 00:00:00', 'MONTH', 798.0536, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (464, '2026-01-25 11:55:29.183939', '2025-02-01 00:00:00', 'MONTH', 815.4451, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (465, '2026-01-25 11:55:29.190783', '2026-01-25 00:00:00', 'DAY', 26.9605, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (466, '2026-01-25 11:55:29.198231', '2026-01-24 00:00:00', 'DAY', 23.9184, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (467, '2026-01-25 11:55:29.207579', '2026-01-23 00:00:00', 'DAY', 24.5731, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (468, '2026-01-25 11:55:29.213887', '2026-01-22 00:00:00', 'DAY', 30.9268, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (469, '2026-01-25 11:55:29.218443', '2026-01-21 00:00:00', 'DAY', 31.3382, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (470, '2026-01-25 11:55:29.224494', '2026-01-20 00:00:00', 'DAY', 31.0491, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (471, '2026-01-25 11:55:29.230107', '2026-01-19 00:00:00', 'DAY', 31.3897, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (472, '2026-01-25 11:55:29.236943', '2026-01-18 00:00:00', 'DAY', 28.8922, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (473, '2026-01-25 11:55:29.243456', '2026-01-17 00:00:00', 'DAY', 33.1801, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (474, '2026-01-25 11:55:29.249911', '2026-01-16 00:00:00', 'DAY', 30.0015, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (475, '2026-01-25 11:55:29.257413', '2026-01-15 00:00:00', 'DAY', 32.7977, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (476, '2026-01-25 11:55:29.276243', '2026-01-14 00:00:00', 'DAY', 24.9631, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (477, '2026-01-25 11:55:29.287738', '2026-01-13 00:00:00', 'DAY', 25.6150, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (478, '2026-01-25 11:55:29.29193', '2026-01-12 00:00:00', 'DAY', 32.5569, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (479, '2026-01-25 11:55:29.295297', '2026-01-11 00:00:00', 'DAY', 27.1535, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (480, '2026-01-25 11:55:29.298542', '2026-01-10 00:00:00', 'DAY', 32.1862, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (481, '2026-01-25 11:55:29.301345', '2026-01-09 00:00:00', 'DAY', 25.6745, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (482, '2026-01-25 11:55:29.304447', '2026-01-08 00:00:00', 'DAY', 32.8025, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (483, '2026-01-25 11:55:29.30786', '2026-01-07 00:00:00', 'DAY', 24.7506, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (484, '2026-01-25 11:55:29.311459', '2026-01-06 00:00:00', 'DAY', 27.3525, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (485, '2026-01-25 11:55:29.314421', '2026-01-05 00:00:00', 'DAY', 31.4732, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (486, '2026-01-25 11:55:29.319105', '2026-01-04 00:00:00', 'DAY', 26.0375, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (487, '2026-01-25 11:55:29.323269', '2026-01-03 00:00:00', 'DAY', 30.8556, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (488, '2026-01-25 11:55:29.32712', '2026-01-02 00:00:00', 'DAY', 25.5423, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (489, '2026-01-25 11:55:29.331866', '2026-01-01 00:00:00', 'DAY', 25.9874, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (490, '2026-01-25 11:55:29.336461', '2025-12-31 00:00:00', 'DAY', 24.0562, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (491, '2026-01-25 11:55:29.340485', '2025-12-30 00:00:00', 'DAY', 26.5013, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (492, '2026-01-25 11:55:29.343367', '2025-12-29 00:00:00', 'DAY', 27.0917, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (493, '2026-01-25 11:55:29.346078', '2025-12-28 00:00:00', 'DAY', 30.5819, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (494, '2026-01-25 11:55:29.349061', '2025-12-27 00:00:00', 'DAY', 27.5594, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (495, '2026-01-25 11:55:29.351792', '2026-01-01 00:00:00', 'YEAR', 8997.0561, 14, 29);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (496, '2026-01-25 11:55:29.359829', '2026-01-01 00:00:00', 'MONTH', 656.6890, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (497, '2026-01-25 11:55:29.364332', '2025-12-01 00:00:00', 'MONTH', 682.5469, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (498, '2026-01-25 11:55:29.368274', '2025-11-01 00:00:00', 'MONTH', 660.1742, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (499, '2026-01-25 11:55:29.373463', '2025-10-01 00:00:00', 'MONTH', 616.5773, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (500, '2026-01-25 11:55:29.378487', '2025-09-01 00:00:00', 'MONTH', 631.8138, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (501, '2026-01-25 11:55:29.38272', '2025-08-01 00:00:00', 'MONTH', 705.8496, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (502, '2026-01-25 11:55:29.38877', '2025-07-01 00:00:00', 'MONTH', 673.7675, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (503, '2026-01-25 11:55:29.394814', '2025-06-01 00:00:00', 'MONTH', 648.9199, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (504, '2026-01-25 11:55:29.399455', '2025-05-01 00:00:00', 'MONTH', 679.1122, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (505, '2026-01-25 11:55:29.403538', '2025-04-01 00:00:00', 'MONTH', 679.7052, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (506, '2026-01-25 11:55:29.407906', '2025-03-01 00:00:00', 'MONTH', 649.0220, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (507, '2026-01-25 11:55:29.411143', '2025-02-01 00:00:00', 'MONTH', 643.7778, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (508, '2026-01-25 11:55:29.414973', '2026-01-25 00:00:00', 'DAY', 23.6034, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (509, '2026-01-25 11:55:29.419823', '2026-01-24 00:00:00', 'DAY', 22.3675, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (510, '2026-01-25 11:55:29.423948', '2026-01-23 00:00:00', 'DAY', 24.6254, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (511, '2026-01-25 11:55:29.427942', '2026-01-22 00:00:00', 'DAY', 23.4487, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (512, '2026-01-25 11:55:29.433967', '2026-01-21 00:00:00', 'DAY', 22.1861, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (513, '2026-01-25 11:55:29.43993', '2026-01-20 00:00:00', 'DAY', 26.0142, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (514, '2026-01-25 11:55:29.445046', '2026-01-19 00:00:00', 'DAY', 21.9327, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (515, '2026-01-25 11:55:29.44987', '2026-01-18 00:00:00', 'DAY', 27.5101, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (516, '2026-01-25 11:55:29.455334', '2026-01-17 00:00:00', 'DAY', 28.0912, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (517, '2026-01-25 11:55:29.460825', '2026-01-16 00:00:00', 'DAY', 27.8882, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (518, '2026-01-25 11:55:29.465825', '2026-01-15 00:00:00', 'DAY', 26.8453, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (519, '2026-01-25 11:55:29.470678', '2026-01-14 00:00:00', 'DAY', 27.6562, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (520, '2026-01-25 11:55:29.475007', '2026-01-13 00:00:00', 'DAY', 30.1773, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (521, '2026-01-25 11:55:29.477671', '2026-01-12 00:00:00', 'DAY', 20.5329, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (522, '2026-01-25 11:55:29.480536', '2026-01-11 00:00:00', 'DAY', 24.8174, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (523, '2026-01-25 11:55:29.48323', '2026-01-10 00:00:00', 'DAY', 30.2684, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (524, '2026-01-25 11:55:29.486091', '2026-01-09 00:00:00', 'DAY', 27.4967, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (525, '2026-01-25 11:55:29.489365', '2026-01-08 00:00:00', 'DAY', 21.7211, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (526, '2026-01-25 11:55:29.49157', '2026-01-07 00:00:00', 'DAY', 27.4047, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (527, '2026-01-25 11:55:29.493594', '2026-01-06 00:00:00', 'DAY', 21.1510, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (528, '2026-01-25 11:55:29.496565', '2026-01-05 00:00:00', 'DAY', 26.8509, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (529, '2026-01-25 11:55:29.499083', '2026-01-04 00:00:00', 'DAY', 24.7033, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (530, '2026-01-25 11:55:29.501383', '2026-01-03 00:00:00', 'DAY', 22.1395, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (531, '2026-01-25 11:55:29.504886', '2026-01-02 00:00:00', 'DAY', 26.5673, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (532, '2026-01-25 11:55:29.507333', '2026-01-01 00:00:00', 'DAY', 24.8896, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (533, '2026-01-25 11:55:29.509513', '2025-12-31 00:00:00', 'DAY', 25.6749, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (534, '2026-01-25 11:55:29.511648', '2025-12-30 00:00:00', 'DAY', 24.0391, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (535, '2026-01-25 11:55:29.513776', '2025-12-29 00:00:00', 'DAY', 27.9636, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (536, '2026-01-25 11:55:29.516142', '2025-12-28 00:00:00', 'DAY', 25.9379, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (537, '2026-01-25 11:55:29.518098', '2025-12-27 00:00:00', 'DAY', 28.1100, 14, 30);
INSERT INTO public.ems_energy_data (id, created_at, data_time, time_type, value, energy_type_id, meter_point_id) VALUES (538, '2026-01-25 11:55:29.520594', '2026-01-01 00:00:00', 'YEAR', 7432.7076, 14, 30);


--
-- Data for Name: ems_energy_saving_project; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_energy_saving_project (id, name, plan, implementation_plan, current_work, liable_person, saving_amount, completion_time, status, remark, created_at, updated_at) VALUES (1, '空压系统节能改造', '对现有空压机组进行变频改造，预计年节电15%', '第一阶段：设备评估（1月）
第二阶段：设备采购（2月）
第三阶段：安装调试（3月）
第四阶段：验收运行（4月）', '设备采购阶段，已完成招标', '张工', 50000.00, '2026-04-30', 'IN_PROGRESS', '投资回收期约2年', '2026-01-06 15:06:50.119695', '2026-01-06 15:06:50.119695');
INSERT INTO public.ems_energy_saving_project (id, name, plan, implementation_plan, current_work, liable_person, saving_amount, completion_time, status, remark, created_at, updated_at) VALUES (2, 'LED照明改造', '将传统照明替换为LED照明，覆盖全厂车间', '第一阶段：调研设计（2周）
第二阶段：分区实施（4周）
第三阶段：效果评估（2周）', '已完成全部改造', '李工', 30000.00, '2025-12-31', 'COMPLETED', '年节电约20万度', '2026-01-06 15:06:50.128387', '2026-01-06 15:06:50.128387');
INSERT INTO public.ems_energy_saving_project (id, name, plan, implementation_plan, current_work, liable_person, saving_amount, completion_time, status, remark, created_at, updated_at) VALUES (3, '烘干炉余热回收222', '利用烘干炉余热进行预热，减少能源消耗', '第一阶段：可行性研究
第二阶段：方案设计
第三阶段：设备选型', '可行性研究阶段', '王工公', 80000.00, '2026-06-30', 'PLANNING', '需要专业设计院支持', '2026-01-06 15:06:50.134101', '2026-02-08 10:17:17.632177');
INSERT INTO public.ems_energy_saving_project (id, name, plan, implementation_plan, current_work, liable_person, saving_amount, completion_time, status, remark, created_at, updated_at) VALUES (6, '电费节约计划', '就是实行梯度用电', '晚上开工，白天休息，', '已经开始', '邓工', 1000000.00, '2026-04-01', 'IN_PROGRESS', '磊', '2026-02-08 10:18:04.76267', '2026-02-08 10:18:17.651653');
INSERT INTO public.ems_energy_saving_project (id, name, plan, implementation_plan, current_work, liable_person, saving_amount, completion_time, status, remark, created_at, updated_at) VALUES (8, 'AutoTest_1770523145752', NULL, NULL, NULL, '系统管理员', NULL, NULL, 'PLANNING', NULL, '2026-02-08 11:59:05.815064', '2026-02-08 11:59:05.815064');


--
-- Data for Name: ems_energy_type; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (5, '2026-01-03 14:58:24.060884', NULL, 'OTHER', 'GASOLINE', 1.4714, '#fa8c16', 8.5000, 2.9251, 'CarOutlined', '汽油', '车用汽油', 5, 0, true, 'L');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (6, '2026-01-03 14:58:24.060884', NULL, 'OTHER', 'DIESEL', 1.4571, '#faad14', 7.8000, 3.0959, 'CarOutlined', '柴油', '车用柴油', 6, 0, true, 'L');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (7, '2026-01-03 14:58:24.060884', NULL, 'STEAM', 'STEAM', 0.1286, '#eb2f96', 120.0000, NULL, 'CloudOutlined', '蒸汽', '工业蒸汽', 7, 0, false, 't');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (8, '2026-01-03 14:58:24.060884', NULL, 'STEAM', 'HOT_WATER', 0.0341, '#f5222d', 35.0000, NULL, 'icon-hotwater', '热水', '供暖热水', 8, 0, false, 'GJ');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (9, '2026-01-03 14:58:24.060884', NULL, 'WATER', 'WATER', 0.0857, '#1677ff', 5.1100, NULL, 'icon-water', '自来水', '市政自来水', 9, 0, true, 't');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (10, '2026-01-03 14:58:24.060884', NULL, 'WATER', 'RECLAIMED_WATER', 0.0286, '#36cfc9', 2.5000, NULL, 'icon-water', '中水', '再生水/中水', 10, 0, true, 't');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (11, '2026-01-03 14:58:24.060884', NULL, 'OTHER', 'COMPRESSED_AIR', 0.0040, '#722ed1', 0.1500, NULL, 'icon-air', '压缩空气', '工业压缩空气', 11, 0, false, 'm³');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (12, '2026-01-03 14:58:24.060884', NULL, 'OTHER', 'OXYGEN', 0.0050, '#2f54eb', 3.5000, NULL, 'icon-oxygen', '氧气', '工业氧气', 12, 0, true, 'm³');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (13, '2026-01-03 14:58:24.060884', NULL, 'OTHER', 'NITROGEN', 0.0045, '#597ef7', 2.8000, NULL, 'icon-nitrogen', '氮气', '工业氮气', 13, 0, true, 'm³');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (14, '2026-01-04 15:56:50.580463', NULL, 'ELECTRIC', 'ELECTRIC', 0.1229, '#1890ff', 1.0000, 0.5703, 'ThunderboltOutlined', '电力', '国网电力，等价折标系数', 1, 0, false, 'kWh');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (3, '2026-01-03 14:58:24.060884', '2026-01-18 09:17:51.988515', 'GAS', 'LNG', 1.7572, '#13c2c2', 5.5000, 2.9388, NULL, '液态天然气', '液化天然气LNG', 3, 0, true, 'kg');
INSERT INTO public.ems_energy_type (id, created_at, updated_at, category, code, coefficient, color, default_price, emission_factor, icon, name, remark, sort_order, status, storable, unit) VALUES (4, '2026-01-03 14:58:24.060884', '2026-01-18 09:18:53.843394', 'OTHER', 'COAL', 0.7143, '#8c8c8c', 800.0000, 2.6930, NULL, '煤炭', '一般烟煤', 4, 0, true, 't');


--
-- Data for Name: ems_energy_unit; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (35, '2026-01-04 10:27:42.627812', '2026-01-04 10:27:42.627812', 'ZC', 0, '泰若总厂', 'Terra EMS 演示总厂', 1, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (36, '2026-01-04 10:27:42.677027', '2026-01-04 10:27:42.677027', 'CJ_JMJG', 1, '精密加工车间', '数控加工设备集中区域', 1, 0, 35, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (37, '2026-01-04 10:27:42.689993', '2026-01-04 10:27:42.689993', 'CJ_ZP', 1, '组装车间', '产品组装生产线', 2, 0, 35, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (38, '2026-01-04 10:27:42.697163', '2026-01-04 10:27:42.697163', 'CJ_ZLJC', 1, '质量监测车间', '质检与测试区域', 3, 0, 35, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (39, '2026-01-04 10:27:42.707108', '2026-01-04 10:27:42.707108', 'CJ_HG', 1, '烘干车间', '烘干固化工艺区', 4, 0, 35, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (40, '2026-01-04 10:27:42.717174', '2026-01-04 10:27:42.717174', 'GY_PDS', 1, '高压配电室', '35kV/10kV变电站', 5, 0, 35, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (41, '2026-01-04 10:27:42.734654', '2026-01-04 10:27:42.734654', 'GY_KYZ', 1, '空压站', '压缩空气集中供应站', 6, 0, 35, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (42, '2026-01-04 10:27:42.765299', '2026-01-04 10:27:42.765299', 'FZ_BGL', 1, '办公楼', '行政办公区域', 7, 0, 35, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (43, '2026-01-04 10:27:42.782403', '2026-01-04 10:27:42.782403', 'FZ_ST', 1, '食堂', '员工餐厅', 8, 0, 35, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (44, '2026-01-04 10:27:42.795765', '2026-01-04 10:27:42.795765', 'FZ_SS', 1, '宿舍楼', '员工住宿区', 9, 0, 35, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (46, '2026-01-04 10:27:42.818759', '2026-01-04 10:27:42.818759', 'JMJG_CX2', 2, '2号产线', '精密磨削产线', 2, 0, 36, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (47, '2026-01-04 10:27:42.838281', '2026-01-04 10:27:42.838281', 'JMJG_CX3', 2, '3号产线', '激光切割产线', 3, 0, 36, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (48, '2026-01-04 10:27:42.847247', '2026-01-04 10:27:42.847247', 'JMJG_FZ', 2, '辅助设施', '冷却系统、照明等', 4, 0, 36, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (49, '2026-01-04 10:27:42.864947', '2026-01-04 10:27:42.864947', 'ZP_ZZ1', 2, '总装线1', '主要产品组装', 1, 0, 37, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (50, '2026-01-04 10:27:42.877816', '2026-01-04 10:27:42.877816', 'ZP_ZZ2', 2, '总装线2', '备用组装线', 2, 0, 37, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (51, '2026-01-04 10:27:42.895715', '2026-01-04 10:27:42.895715', 'ZP_CS', 2, '测试工位', '产品功能测试', 3, 0, 37, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (52, '2026-01-04 10:27:42.906362', '2026-01-04 10:27:42.906362', 'ZP_BZ', 2, '包装区', '产品包装', 4, 0, 37, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (53, '2026-01-04 10:27:42.915502', '2026-01-04 10:27:42.915502', 'PDS_1BY', 2, '1号变压器', '1000kVA变压器', 1, 0, 40, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (54, '2026-01-04 10:27:42.929678', '2026-01-04 10:27:42.929678', 'PDS_2BY', 2, '2号变压器', '800kVA变压器', 2, 0, 40, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (55, '2026-01-04 10:27:42.940156', '2026-01-04 10:27:42.940156', 'PDS_DPBG', 2, '低压配电柜', '400V配电系统', 3, 0, 40, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (56, '2026-01-04 10:27:42.950392', '2026-01-04 10:27:42.950392', 'KYZ_1KYJ', 2, '1号空压机', '螺杆式132kW', 1, 0, 41, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (57, '2026-01-04 10:27:42.981843', '2026-01-04 10:27:42.981843', 'KYZ_2KYJ', 2, '2号空压机', '螺杆式90kW', 2, 0, 41, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (58, '2026-01-04 10:27:42.993807', '2026-01-04 10:27:42.993807', 'KYZ_GZJ', 2, '干燥机', '冷干机组', 3, 0, 41, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (59, '2026-01-04 10:27:43.013355', '2026-01-04 10:27:43.013355', 'BGL_1F', 2, '1楼大厅', '前台接待区', 1, 0, 42, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (60, '2026-01-04 10:27:43.033696', '2026-01-04 10:27:43.033696', 'BGL_2F', 2, '2楼办公区', '行政办公', 2, 0, 42, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (61, '2026-01-04 10:27:43.052237', '2026-01-04 10:27:43.052237', 'BGL_3F', 2, '3楼会议室', '多功能会议室', 3, 0, 42, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (62, '2026-01-04 10:27:43.063817', '2026-01-04 10:27:43.063817', 'BGL_JF', 2, '机房', 'IT数据中心', 4, 0, 42, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (66, '2026-01-04 10:27:43.103028', '2026-01-04 10:27:43.103028', '1BY_JLL', 3, '进线柜', '高压进线', 1, 0, 53, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (67, '2026-01-04 10:27:43.11288', '2026-01-04 10:27:43.11288', '1BY_CLL', 3, '出线柜', '低压出线', 2, 0, 53, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (45, '2026-01-04 10:27:42.805877', '2026-01-18 16:29:29.592296', 'JMJG_CX1', 2, '1号产线', 'CNC加工中心产线', 1, 0, 36, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (63, '2026-01-04 10:27:43.075722', '2026-01-18 16:29:29.592821', 'CX1_CNC01', 3, 'CNC-01', '五轴加工中心', 1, 0, 45, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (64, '2026-01-04 10:27:43.086683', '2026-01-18 16:29:29.592929', 'CX1_CNC02', 3, 'CNC-02', '四轴加工中心', 2, 0, 45, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (65, '2026-01-04 10:27:43.096414', '2026-01-18 16:29:29.593022', 'CX1_CNC03', 3, 'CNC-03', '三轴加工中心', 3, 0, 45, NULL, NULL, NULL, NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (70, '2026-01-18 16:13:31.152637', '2026-01-18 16:29:29.594071', 'CXC_CNC04', 3, 'CNC-04', 'CNC04', 4, 0, 45, NULL, NULL, 'GENERAL', NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (71, '2026-01-18 16:14:01.1469', '2026-01-18 16:29:29.594173', 'CXC_CNC05', 3, 'CNC-05', NULL, 4, 0, 45, NULL, NULL, 'GENERAL', NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (73, '2026-01-18 16:30:06.828392', '2026-01-18 16:30:26.948972', 'CXC_CNC-011', 4, 'CNC-011', NULL, 0, 0, 64, NULL, NULL, 'GENERAL', NULL);
INSERT INTO public.ems_energy_unit (id, created_at, updated_at, code, level, name, remark, sort_order, status, parent_id, rated_current, rated_power, unit_type, voltage_level) VALUES (1, '2026-01-25 10:56:07.035814', '2026-01-25 10:56:07.035814', 'EU_001', 0, '总进线柜', NULL, 1, 0, NULL, NULL, NULL, 'GENERAL', NULL);


--
-- Data for Name: ems_energy_unit_point; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (11, 36);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (1, 36);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (9, 37);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (10, 37);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (1, 37);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (9, 40);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (10, 40);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (11, 40);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (1, 40);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (12, 42);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (13, 42);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (1, 42);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (12, 43);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (14, 43);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (15, 43);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (1, 43);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (18, 39);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (19, 39);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (20, 39);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (21, 39);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (1, 39);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (11, 41);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (1, 41);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (23, 45);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (1, 45);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (11, 53);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (1, 53);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (5, 40);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (5, 65);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (5, 37);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (6, 40);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (6, 65);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (6, 37);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (7, 40);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (7, 37);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (8, 40);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (8, 65);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (8, 37);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (26, 38);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (27, 44);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (28, 46);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (29, 47);
INSERT INTO public.ems_energy_unit_point (meter_point_id, energy_unit_id) VALUES (30, 48);


--
-- Data for Name: ems_knowledge_article; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (4, '系统', '行业动态', '全球氢能产业发展趋势分析报告，探讨绿氢在零碳工厂中的应用。', 12, 4, 0, '氢能产业趋势分析', '氢能与未来零碳工厂展望', 330, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (5, '能耗办', '节能案例', '某汽车零件生产基地通过余热回收系统实现综合能耗降低15%的案例。', 8, 5, 0, '余热回收节能案例', '余热回收在汽车制造业的应用', 150, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (6, '管理员', '技术规范', '中水回用系统的计量器具安装位置及数据采集精度要求标准。', 10, 6, 0, '中水回用计量标准', '中水回用系统计量技术要求', 60, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (7, '专家组', '政策法规', '各省市阶梯电价及峰谷平电价政策汇编（2025版）。', NULL, 7, 0, '最新电价政策汇编', '全国峰谷电价政策深度解读', 450, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (8, '技术部', '操作手册', '循环冷却水系统加药量计算方法及水质监测标准步骤。', 9, 8, 0, '循环水日常维护', '循环冷却水水质管理手册', 180, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (9, '管理员', '技术规范', '工业氧气/氮气存储区安全防范及浓度监控报警系统配置规范。', 13, 9, 0, '工业气体安全规范', '氧氮气存储区域监控技术规范', 95, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (10, '能耗办', '节能案例', '煤改气工程后的能效评估对比，分析液态天然气的经济性。', 3, 10, 0, '煤改气能效评估', '液态天然气在替代煤炭中的应用', 220, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (11, '管理员', '技术规范', '中央空调末端设备群控策略优化，实现夏季办公区环境恒温节能。', NULL, 11, 0, '中央空调节能策略', '中央空调末端精细化控制规范', 140, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (12, '技术部', '操作手册', '光伏发电系统清洁频次及逆变器运行状态巡检作业指导书。', NULL, 12, 0, '光伏维护作业指导', '分布式光伏系统运维手册', 175, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (13, '专家组', '政策法规', '重点用能单位能源管理体系建设要求（GB/T 23331-2020）解读。', NULL, 13, 0, '能源管理体系标准', 'ISO 50001/GBT 23331 深度解析', 310, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (14, '能耗办', '节能案例', '电机系统节能改造：变频技术在高压水泵中的应用实测。', 9, 14, 0, '电机变频改造案例', '高压水泵变频节能改造报告', 190, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (15, '管理员', '技术规范', '能源计量器具仪表数据格式化及接口协议（Modbus TCP）通讯规范。', NULL, 15, 0, '数据通讯接口规范', '能源系统数据采集协议标准', 110, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (16, '系统', '行业动态', '碳捕捉技术（CCUS）在水泥行业的示范项目进展及成本分析。', NULL, 16, 0, '碳捕捉技术进展', '水泥行业CCUS项目实践回顾', 80, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (17, '技术部', '操作手册', '厂区柴油发电机组备用动力切换及月度负荷测试操作步骤。', 6, 17, 0, '柴油发电机维护', '备用电源系统定期测试规程', 130, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (18, '专家组', '政策法规', '国家对煤炭消费减量替代的补贴申请流程及材料清单说明。', 4, 18, 0, '煤炭替代补贴政策', '煤炭减量替代专项补贴申报指南', 200, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (19, '管理员', '技术规范', '宿舍区热水系统热泵效率监测及用水定额管理办法。', 8, 19, 0, '热泵热水管理', '热水系统效率提升与定额管理', 55, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (20, '能耗办', '节能案例', '综合楼LED照明改造及智能感应控制系统带来的节能收益。', NULL, 20, 0, '照明节能改造', 'LED照明与智能控制系统应用', 165, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (3, '技术部', '操作手册', '新型超高效蒸汽锅炉的安全操作程序及日常维护保养要点。', 7, 3, 0, '蒸汽锅炉操作手册', '超高效蒸汽锅炉运行规程', 212, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (1, '管理员', '技术规范', '工厂压缩空气系统节能运行技术规范，包括泄漏检测与压力优化。', 11, 1, 0, '压缩空气系统节能规范', '压缩空气系统节能技术指南', 122, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');
INSERT INTO public.ems_knowledge_article (id, author, category, content, energy_type_id, sort_order, status, summary, title, view_count, created_at, updated_at) VALUES (2, '专家组', '政策法规', '国家关于“十四五”期间工业节能节水的重点任务与考核指标说明。', 9, 2, 0, '十四五工业节能政策', '工业节能节水重点任务解析', 88, '2026-01-31 06:19:39.741834', '2026-01-31 06:19:39.741834');


--
-- Data for Name: ems_meter; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (4, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 365, 'HW-001', 'GW-002', '锅炉房供热出口', '开封仪表', NULL, '0-9999 GJ', 'DNCG-DN100', '采暖热水总表', '李工', '2023-01-10', '园区采暖热水总计量', 30, '2023-01-01', 0, 'HEAT_METER', NULL, 8);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (5, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 365, 'HW-002', 'GW-003', '办公楼地下室热力站', '开封仪表', NULL, '0-999 GJ', 'DNCG-DN50', '办公楼热水表', '李工', '2023-01-25', NULL, 30, '2023-01-15', 0, 'HEAT_METER', NULL, 8);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (6, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 730, 'WM-001', 'GW-001', '厂区进水总阀门处', '宁波水表', NULL, '0-99999 m³', 'LXLC-50', '生产用水总表', '陈工', '2023-01-10', '全厂生产用水总计量', 60, '2023-01-01', 0, 'WATER_METER', NULL, 9);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (7, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 730, 'WM-002', 'GW-001', '冷却塔泵房', '宁波水表', NULL, '0-9999 m³', 'LXS-25', '冷却塔补水表', '陈工', '2023-04-15', NULL, 60, '2023-04-01', 0, 'WATER_METER', NULL, 9);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (8, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 730, 'WM-003', NULL, '宿舍楼地下室', '宁波水表', NULL, '0-99999 m³', 'LXS-50', '生活区用水表', '陈工', '2022-03-15', '员工宿舍及食堂用水', 60, '2022-03-01', 0, 'WATER_METER', NULL, 9);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (9, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 730, 'RW-001', 'GW-001', '中水处理站出水口', '宁波水表', NULL, '0-99999 m³', 'LXLC-80', '中水回用总表', '陈工', '2023-05-15', '中水回用系统总计量', 60, '2023-05-01', 0, 'WATER_METER', NULL, 10);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (10, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 730, 'RW-002', NULL, '绿化灌溉泵房', '宁波水表', NULL, '0-9999 m³', 'LXS-25', '绿化用中水表', '陈工', '2023-05-20', '厂区绿化用中水', 60, '2023-05-10', 0, 'WATER_METER', NULL, 10);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (11, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 365, 'LNG-001', 'GW-002', 'LNG储罐区', '艾默生', NULL, '0-9999 kg', 'CMF200', 'LNG储罐计量表', '周工', '2023-01-20', 'LNG储罐进料计量', 30, '2023-01-01', 0, 'GAS_METER', NULL, 3);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (12, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 365, 'LNG-002', 'GW-002', 'LNG气化站', '艾默生', NULL, '0-9999 kg', 'CMF100', 'LNG气化站出口表', '周工', '2023-02-15', 'LNG气化后计量', 30, '2023-02-01', 0, 'GAS_METER', NULL, 3);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (13, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 365, 'CA-001', 'GW-001', '空压机房出口', 'CS仪表', NULL, '0-99999 m³', 'VA520-DN80', '压缩空气总表', '王工', '2023-03-10', '全厂压缩空气总供给量', 30, '2023-03-01', 0, 'GAS_METER', NULL, 11);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (14, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 365, 'CA-002', 'GW-001', '一车间进气口', 'CS仪表', NULL, '0-9999 m³', 'VA520-DN50', '一车间压缩空气表', '王工', '2023-03-25', NULL, 30, '2023-03-15', 0, 'GAS_METER', NULL, 11);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (15, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 365, 'CA-003', 'GW-001', '二车间进气口', 'CS仪表', NULL, '0-9999 m³', 'VA520-DN50', '二车间压缩空气表', '王工', '2023-03-25', NULL, 30, '2023-03-15', 0, 'GAS_METER', NULL, 11);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (16, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 365, 'O2-001', 'GW-003', '制氧站出口', '迈捷科', NULL, '0-9999 m³', 'MF5700-DN25', '氧气站总表', '李工', '2023-04-15', '工业氧气总供给计量', 30, '2023-04-01', 0, 'GAS_METER', NULL, 12);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (17, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 365, 'N2-001', 'GW-003', '制氮站出口', '迈捷科', NULL, '0-99999 m³', 'MF5700-DN50', '氮气站总表', '李工', '2023-04-15', '工业氮气总供给计量', 30, '2023-04-01', 0, 'GAS_METER', NULL, 13);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (18, '2026-01-04 05:53:51.883767', '2026-01-04 05:53:51.883767', 365, 'N2-002', 'GW-003', '包装车间进气口', '迈捷科', NULL, '0-9999 m³', 'MF5700-DN25', '包装车间氮气表', '李工', '2023-05-10', '包装充氮用氮气计量', 30, '2023-05-01', 0, 'GAS_METER', NULL, 13);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (19, '2026-01-04 05:53:51.916787', '2026-01-04 05:53:51.916787', 730, 'WM-OLD-001', NULL, '一车间(已更换)', '宁波水表', NULL, '0-9999 m³', 'LXS-15', '旧水表(已更换)', '陈工', '2015-02-01', '2023年6月因精度问题更换', 60, '2015-01-01', 1, 'WATER_METER', NULL, 9);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (20, '2026-01-04 05:53:51.916787', '2026-01-04 05:53:51.916787', 365, 'HM-OLD-001', NULL, '旧厂房(已拆除)', '大连理工', NULL, '0-200 t', 'LWGY-DN40', '旧蒸汽表(已拆除)', '周工', '2016-02-01', '2023年12月随厂房拆除', 30, '2016-01-01', 1, 'HEAT_METER', NULL, 7);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (1, '2026-01-04 05:53:51.883767', '2026-01-18 11:08:17.392337', 365, 'HM-001', NULL, '一车间蒸汽管道入口', '大连理工', NULL, '0-999 t', 'LWGY-DN80', '一车间蒸汽表', '周工', '2023-02-28', '一车间蒸汽用量计量，涡街流量计', 30, '2023-02-01', 0, 'HEAT_METER', NULL, 7);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (2, '2026-01-04 05:53:51.883767', '2026-01-18 11:08:48.761529', 365, 'HM-002', NULL, '烘干车间蒸汽分汽缸', '横河仪器', NULL, '0-9999 t', 'LUGB-DN100', '烘干车间蒸汽表', '周工22', '2023-03-15', NULL, 30, '2023-03-01', 0, 'HEAT_METER', NULL, 7);
INSERT INTO public.ems_meter (id, created_at, updated_at, check_cycle, code, gateway_id, location, manufacturer, max_power, measure_range, model_number, name, person_charge, putrun_time, remark, reminder_cycle, start_time, status, type, wire_diameter, energy_type_id) VALUES (3, '2026-01-04 05:53:51.883767', '2026-01-18 11:39:36.977425', 365, 'HM-003', NULL, '二车间蒸汽管道入口', '横河仪器', NULL, '0-500 t', 'LWGY-DN50', '二车间蒸汽表', '周工', '2023-06-10', '二车间蒸汽用量计量', 30, '2023-06-01', 0, 'HEAT_METER', NULL, 7);


--
-- Data for Name: ems_meter_point; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (9, '2026-01-04 11:23:46.126356', '2026-01-04 11:23:46.126356', 'ENERGY', 'PT_U_B', NULL, NULL, NULL, 'B相电压', 'COLLECT', NULL, 9, 0, NULL, NULL, 'V', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (10, '2026-01-04 11:23:46.126356', '2026-01-04 11:23:46.126356', 'ENERGY', 'PT_U_C', NULL, NULL, NULL, 'C相电压', 'COLLECT', NULL, 10, 0, NULL, NULL, 'V', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (11, '2026-01-04 11:23:46.126356', '2026-01-04 11:23:46.126356', 'EFFICIENCY', 'PT_PF', NULL, NULL, NULL, '功率因数', 'COLLECT', '综合功率因数', 11, 0, NULL, NULL, '', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (12, '2026-01-04 11:23:46.14288', '2026-01-04 11:23:46.14288', 'ENERGY', 'PT_WATER_TOTAL', 0.0000, NULL, NULL, '用水量', 'COLLECT', '累计用水量', 1, 0, NULL, NULL, 'm³', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (13, '2026-01-04 11:23:46.14288', '2026-01-04 11:23:46.14288', 'ENERGY', 'PT_WATER_FLOW', NULL, NULL, NULL, '瞬时流量', 'COLLECT', '瞬时水流量', 2, 0, NULL, NULL, 'm³/h', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (14, '2026-01-04 11:23:46.150806', '2026-01-04 11:23:46.150806', 'ENERGY', 'PT_GAS_TOTAL', 0.0000, NULL, NULL, '用气量', 'COLLECT', '累计用气量', 1, 0, NULL, NULL, 'm³', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (15, '2026-01-04 11:23:46.150806', '2026-01-04 11:23:46.150806', 'ENERGY', 'PT_GAS_FLOW', NULL, NULL, NULL, '瞬时流量', 'COLLECT', '瞬时气流量', 2, 0, NULL, NULL, 'm³/h', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (16, '2026-01-04 11:23:46.150806', '2026-01-04 11:23:46.150806', 'OTHER', 'PT_GAS_TEMP', NULL, NULL, NULL, '气体温度', 'COLLECT', '温度补偿用', 3, 0, NULL, NULL, '℃', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (17, '2026-01-04 11:23:46.150806', '2026-01-04 11:23:46.150806', 'OTHER', 'PT_GAS_PRESS', NULL, NULL, NULL, '气体压力', 'COLLECT', '压力补偿用', 4, 0, NULL, NULL, 'kPa', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (18, '2026-01-04 11:23:46.160963', '2026-01-04 11:23:46.160963', 'ENERGY', 'PT_STEAM_TOTAL', 0.0000, NULL, NULL, '蒸汽用量', 'COLLECT', '累计蒸汽用量', 1, 0, NULL, NULL, 't', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (19, '2026-01-04 11:23:46.160963', '2026-01-04 11:23:46.160963', 'ENERGY', 'PT_STEAM_FLOW', NULL, NULL, NULL, '瞬时流量', 'COLLECT', '瞬时蒸汽流量', 2, 0, NULL, NULL, 't/h', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (20, '2026-01-04 11:23:46.160963', '2026-01-04 11:23:46.160963', 'OTHER', 'PT_STEAM_TEMP', NULL, NULL, NULL, '蒸汽温度', 'COLLECT', NULL, 3, 0, NULL, NULL, '℃', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (21, '2026-01-04 11:23:46.160963', '2026-01-04 11:23:46.160963', 'OTHER', 'PT_STEAM_PRESS', NULL, NULL, NULL, '蒸汽压力', 'COLLECT', NULL, 4, 0, NULL, NULL, 'MPa', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (22, '2026-01-04 11:23:46.169818', '2026-01-04 11:23:46.169818', 'OPERATION', 'PT_ENERGY_COST', NULL, NULL, NULL, '能源成本', 'CALC', '根据用量和单价计算', 1, 0, NULL, NULL, '元', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (23, '2026-01-04 11:23:46.169818', '2026-01-04 11:23:46.169818', 'EFFICIENCY', 'PT_UNIT_ENERGY', NULL, NULL, NULL, '单位产品能耗', 'CALC', '能耗/产量', 2, 0, NULL, NULL, 'kWh/件', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (24, '2026-01-04 11:23:46.169818', '2026-01-04 11:23:46.169818', 'OTHER', 'PT_CARBON_EMIT', NULL, NULL, NULL, '碳排放量', 'CALC', '根据能耗计算碳排放', 3, 0, NULL, NULL, 'tCO2', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (2, '2026-01-04 11:23:46.126356', '2026-01-18 17:22:17.598209', 'ENERGY', 'PT_EQ_TOTAL', 0.0000, NULL, NULL, '无功电量', 'COLLECT', '正向无功总电量', 2, 0, NULL, NULL, 'kvarh', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (3, '2026-01-04 11:23:46.126356', '2026-01-18 17:31:11.558051', 'PRODUCT', 'PT_P_INST', NULL, NULL, NULL, '瞬时有功功率', 'COLLECT', '瞬时有功功率', 3, 0, NULL, NULL, 'kW', 14, 4);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (4, '2026-01-04 11:23:46.126356', '2026-01-18 18:33:50.030253', 'PRODUCT', 'PT_Q_INST', NULL, NULL, NULL, '瞬时无功功率', 'COLLECT', '瞬时无功功率', 4, 0, NULL, NULL, 'kvar', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (5, '2026-01-04 11:23:46.126356', '2026-01-21 07:21:33.68876', 'ENERGY', 'PT_I_A', NULL, NULL, NULL, 'A相电流', 'COLLECT', NULL, 5, 0, NULL, NULL, 'A', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (6, '2026-01-04 11:23:46.126356', '2026-01-21 07:21:33.750856', 'ENERGY', 'PT_I_B', NULL, NULL, NULL, 'B相电流', 'COLLECT', NULL, 6, 0, NULL, NULL, 'A', NULL, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (7, '2026-01-04 11:23:46.126356', '2026-01-21 07:22:48.694444', 'ENERGY', 'PT_I_C', NULL, NULL, NULL, 'C相电流', 'COLLECT', NULL, 7, 0, NULL, NULL, 'A', 14, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (8, '2026-01-04 11:23:46.126356', '2026-01-21 07:24:15.22749', 'ENERGY', 'PT_U_A', NULL, NULL, NULL, 'A相电压', 'COLLECT', NULL, 8, 0, NULL, NULL, 'V', 14, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (26, '2026-01-25 11:53:45.56597', '2026-01-25 11:53:45.56597', NULL, 'DEMO_CJ_ZLJC', NULL, NULL, NULL, '质量监测车间演示点位', 'COLLECT', NULL, 0, 0, NULL, NULL, NULL, 14, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (27, '2026-01-25 11:53:46.184596', '2026-01-25 11:53:46.184596', NULL, 'DEMO_FZ_SS', NULL, NULL, NULL, '宿舍楼演示点位', 'COLLECT', NULL, 0, 0, NULL, NULL, NULL, 14, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (28, '2026-01-25 11:55:28.907286', '2026-01-25 11:55:28.907286', NULL, 'DEMO_JMJG_CX2', NULL, NULL, NULL, '2号产线演示点位', 'COLLECT', NULL, 0, 0, NULL, NULL, NULL, 14, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (29, '2026-01-25 11:55:29.113287', '2026-01-25 11:55:29.113287', NULL, 'DEMO_JMJG_CX3', NULL, NULL, NULL, '3号产线演示点位', 'COLLECT', NULL, 0, 0, NULL, NULL, NULL, 14, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (30, '2026-01-25 11:55:29.355065', '2026-01-25 11:55:29.355065', NULL, 'DEMO_JMJG_FZ', NULL, NULL, NULL, '辅助设施演示点位', 'COLLECT', NULL, 0, 0, NULL, NULL, NULL, 14, NULL);
INSERT INTO public.ems_meter_point (id, created_at, updated_at, category, code, initial_value, max_value, min_value, name, point_type, remark, sort_order, status, step_max, step_min, unit, energy_type_id, meter_id) VALUES (1, '2026-01-04 11:23:46.126356', '2026-01-04 19:58:07.440038', 'ENERGY', 'PT_EP_TOTAL', 0.0000, NULL, NULL, '有功电量', 'COLLECT', '正向有功总电量', 1, 0, NULL, NULL, 'kWh', NULL, NULL);


--
-- Data for Name: ems_peak_valley_data; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (29, 15.7500, '2026-01-25 10:56:07.084891', '2025-01-01', 10.5000, 1, 1, 'SHARP', 1.5000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (30, 26.4000, '2026-01-25 10:56:07.084891', '2025-01-01', 22.0000, 1, 1, 'PEAK', 1.2000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (31, 28.0000, '2026-01-25 10:56:07.084891', '2025-01-01', 35.0000, 1, 1, 'FLAT', 0.8000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (32, 7.2000, '2026-01-25 10:56:07.084891', '2025-01-01', 18.0000, 1, 1, 'VALLEY', 0.4000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (33, 16.5000, '2026-01-25 10:56:07.084891', '2025-01-02', 11.0000, 1, 1, 'SHARP', 1.5000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (34, 28.2000, '2026-01-25 10:56:07.084891', '2025-01-02', 23.5000, 1, 1, 'PEAK', 1.2000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (35, 28.8000, '2026-01-25 10:56:07.084891', '2025-01-02', 36.0000, 1, 1, 'FLAT', 0.8000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (36, 7.0000, '2026-01-25 10:56:07.084891', '2025-01-02', 17.5000, 1, 1, 'VALLEY', 0.4000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (37, 18.0000, '2026-01-25 10:56:07.084891', '2025-01-03', 12.0000, 1, 1, 'SHARP', 1.5000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (38, 25.2000, '2026-01-25 10:56:07.084891', '2025-01-03', 21.0000, 1, 1, 'PEAK', 1.2000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (39, 30.4000, '2026-01-25 10:56:07.084891', '2025-01-03', 38.0000, 1, 1, 'FLAT', 0.8000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (40, 8.0000, '2026-01-25 10:56:07.084891', '2025-01-03', 20.0000, 1, 1, 'VALLEY', 0.4000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (41, 14.2500, '2026-01-25 10:56:07.084891', '2025-01-04', 9.5000, 1, 1, 'SHARP', 1.5000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (42, 22.8000, '2026-01-25 10:56:07.084891', '2025-01-04', 19.0000, 1, 1, 'PEAK', 1.2000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (43, 25.6000, '2026-01-25 10:56:07.084891', '2025-01-04', 32.0000, 1, 1, 'FLAT', 0.8000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (44, 10.0000, '2026-01-25 10:56:07.084891', '2025-01-04', 25.0000, 1, 1, 'VALLEY', 0.4000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (45, 12.0000, '2026-01-25 10:56:07.084891', '2025-01-05', 8.0000, 1, 1, 'SHARP', 1.5000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (46, 18.0000, '2026-01-25 10:56:07.084891', '2025-01-05', 15.0000, 1, 1, 'PEAK', 1.2000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (47, 22.4000, '2026-01-25 10:56:07.084891', '2025-01-05', 28.0000, 1, 1, 'FLAT', 0.8000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (48, 12.0000, '2026-01-25 10:56:07.084891', '2025-01-05', 30.0000, 1, 1, 'VALLEY', 0.4000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (49, 19.5000, '2026-01-25 10:56:07.084891', '2025-01-06', 13.0000, 1, 1, 'SHARP', 1.5000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (50, 30.0000, '2026-01-25 10:56:07.084891', '2025-01-06', 25.0000, 1, 1, 'PEAK', 1.2000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (51, 32.0000, '2026-01-25 10:56:07.084891', '2025-01-06', 40.0000, 1, 1, 'FLAT', 0.8000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (52, 6.0000, '2026-01-25 10:56:07.084891', '2025-01-06', 15.0000, 1, 1, 'VALLEY', 0.4000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (53, 21.0000, '2026-01-25 10:56:07.084891', '2025-01-07', 14.0000, 1, 1, 'SHARP', 1.5000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (54, 31.2000, '2026-01-25 10:56:07.084891', '2025-01-07', 26.0000, 1, 1, 'PEAK', 1.2000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (55, 33.6000, '2026-01-25 10:56:07.084891', '2025-01-07', 42.0000, 1, 1, 'FLAT', 0.8000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (56, 5.6000, '2026-01-25 10:56:07.084891', '2025-01-07', 14.0000, 1, 1, 'VALLEY', 0.4000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (57, 15.7500, '2026-01-25 11:04:53.540467', '2025-01-01', 10.5000, 1, 1, 'SHARP', 1.5000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (58, 26.4000, '2026-01-25 11:04:53.540467', '2025-01-01', 22.0000, 1, 1, 'PEAK', 1.2000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (59, 28.0000, '2026-01-25 11:04:53.540467', '2025-01-01', 35.0000, 1, 1, 'FLAT', 0.8000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (60, 7.2000, '2026-01-25 11:04:53.540467', '2025-01-01', 18.0000, 1, 1, 'VALLEY', 0.4000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (61, 15.7500, '2026-01-25 11:09:02.231742', '2025-01-01', 10.5000, 1, 1, 'SHARP', 1.5000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (62, 26.4000, '2026-01-25 11:09:02.231742', '2025-01-01', 22.0000, 1, 1, 'PEAK', 1.2000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (63, 28.0000, '2026-01-25 11:09:02.231742', '2025-01-01', 35.0000, 1, 1, 'FLAT', 0.8000, 'DAY');
INSERT INTO public.ems_peak_valley_data (id, cost, created_at, data_time, electricity, energy_unit_id, meter_point_id, period_type, price, time_type) VALUES (64, 7.2000, '2026-01-25 11:09:02.231742', '2025-01-01', 18.0000, 1, 1, 'VALLEY', 0.4000, 'DAY');


--
-- Data for Name: ems_policy; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_policy (id, title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES (1, '中华人民共和国节约能源法', 'NATIONAL', '全国人民代表大会常务委员会', '2018-10-26', 'https://flk.npc.gov.cn/detail2.html?ZmY4MDgwODE2ZjNjYmIzYzAxNmY0MTBhY2YxYjAxNGY%3D', '国家实行节约资源的基本国策，国家实施节约与开发并举、把节约放在首位的能源发展战略，鼓励、支持节能科学技术的研究、开发、示范和推广。', 0, '主要法律依据', '2026-01-06 15:06:50.163917', '2026-01-06 15:06:50.163917');
INSERT INTO public.ems_policy (id, title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES (2, '工业节能诊断服务行动计划', 'NATIONAL', '工业和信息化部', '2019-05-27', 'https://www.miit.gov.cn/zwgk/zcwj/wjfb/txy/art/2020/art_d5f4d2e0c3614da2a3e1a6b3f0b2c5d7.html', '推动开展工业节能诊断服务，帮助企业发掘节能潜力，提高能效水平。', 0, '节能诊断参考', '2026-01-06 15:06:50.171668', '2026-01-06 15:06:50.171668');
INSERT INTO public.ems_policy (id, title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES (3, '广东省节约能源条例', 'LOCAL', '广东省人民代表大会常务委员会', '2022-11-30', NULL, '本省行政区域内从事和涉及能源生产、经营、消费及其相关活动的单位和个人适用本条例。', 0, '地方性法规', '2026-01-06 15:06:50.177038', '2026-01-06 15:06:50.177038');
INSERT INTO public.ems_policy (id, title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES (4, '电机系统节能技术规范', 'INDUSTRY', '国家市场监督管理总局', '2020-06-01', NULL, '规定了电机系统节能的技术要求、检测方法和评价准则。', 0, '技术标准', '2026-01-06 15:06:50.181913', '2026-01-06 15:06:50.181913');
INSERT INTO public.ems_policy (id, title, type, department, issuing_date, file_url, summary, status, remark, created_at, updated_at) VALUES (5, '重点用能单位能耗在线监测系统技术规范222', 'NATIONAL', '国家发展和改革委员会', '2018-01-01', NULL, '规定了重点用能单位能耗在线监测系统的技术要求、数据采集、传输和应用等内容。222', 0, '能耗监测依据222', '2026-01-06 15:06:50.189309', '2026-02-08 11:00:28.670471');


--
-- Data for Name: ems_price_policy; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_price_policy (id, created_at, updated_at, code, is_multi_rate, name, remark, sort_order, status, energy_type_id, effective_end_date, effective_start_date) VALUES (3, '2026-01-04 15:57:26.730312', '2026-01-04 15:57:26.730312', 'INDUSTRY_TOU_2024', true, '工业分时电价(2024)', '适用于一般工商业用电，采用尖峰平谷电价', 1, 0, 14, '2024-12-31', '2024-01-01');
INSERT INTO public.ems_price_policy (id, created_at, updated_at, code, is_multi_rate, name, remark, sort_order, status, energy_type_id, effective_end_date, effective_start_date) VALUES (4, '2026-01-04 15:57:26.746921', '2026-01-04 15:57:26.746921', 'INDUSTRY_TOU_2025', true, '工业分时电价(2025)', '适用于一般工商业用电，2025年度执行标准', 2, 0, 14, NULL, '2025-01-01');
INSERT INTO public.ems_price_policy (id, created_at, updated_at, code, is_multi_rate, name, remark, sort_order, status, energy_type_id, effective_end_date, effective_start_date) VALUES (5, '2026-01-04 15:57:26.754091', '2026-01-04 15:57:26.754091', 'COMMERCIAL_SINGLE', false, '商业电价', '适用于小型商业用电，统一计价', 3, 0, 14, NULL, '2024-01-01');
INSERT INTO public.ems_price_policy (id, created_at, updated_at, code, is_multi_rate, name, remark, sort_order, status, energy_type_id, effective_end_date, effective_start_date) VALUES (6, '2026-01-20 07:18:46.697866', '2026-01-20 23:27:07.546212', 'INDUSTRY_TOU_02', true, '工业分时电价（2026）', NULL, 4, 0, 14, '2026-12-31', '2026-01-01');


--
-- Data for Name: ems_price_policy_item; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (7, '2026-01-04 15:57:26.762893', '2026-01-04 15:57:26.762893', 'SHARP', 1.2500, NULL, 1, 3, '11:00', '09:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (8, '2026-01-04 15:57:26.773513', '2026-01-04 15:57:26.773513', 'PEAK', 0.9500, NULL, 2, 3, '13:00', '11:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (9, '2026-01-04 15:57:26.781369', '2026-01-04 15:57:26.781369', 'FLAT', 0.6500, NULL, 3, 3, '17:00', '13:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (10, '2026-01-04 15:57:26.787921', '2026-01-04 15:57:26.787921', 'VALLEY', 0.3500, NULL, 4, 3, '22:00', '17:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (11, '2026-01-04 15:57:26.801065', '2026-01-04 15:57:26.801065', 'DEEP', 0.2000, NULL, 5, 3, '09:00', '22:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (12, '2026-01-04 15:57:26.811891', '2026-01-04 15:57:26.811891', 'SHARP', 1.3000, NULL, 1, 4, '11:00', '09:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (13, '2026-01-04 15:57:26.820703', '2026-01-04 15:57:26.820703', 'PEAK', 1.0000, NULL, 2, 4, '13:00', '11:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (14, '2026-01-04 15:57:26.843966', '2026-01-04 15:57:26.843966', 'FLAT', 0.7000, NULL, 3, 4, '17:00', '13:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (15, '2026-01-04 15:57:26.861198', '2026-01-04 15:57:26.861198', 'VALLEY', 0.4000, NULL, 4, 4, '22:00', '17:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (16, '2026-01-04 15:57:26.871882', '2026-01-04 15:57:26.871882', 'DEEP', 0.2500, NULL, 5, 4, '09:00', '22:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (17, '2026-01-04 15:57:26.881667', '2026-01-04 15:57:26.881667', 'FLAT', 0.8200, NULL, 1, 5, '23:59', '00:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (28, '2026-01-20 23:27:07.526889', '2026-01-20 23:27:07.526889', 'DEEP', 0.2000, NULL, 1, 6, '07:00', '00:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (29, '2026-01-20 23:27:07.531061', '2026-01-20 23:27:07.531061', 'FLAT', 0.6000, NULL, 2, 6, '09:00', '07:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (30, '2026-01-20 23:27:07.53253', '2026-01-20 23:27:07.53253', 'PEAK', 0.8000, NULL, 3, 6, '11:00', '09:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (31, '2026-01-20 23:27:07.533797', '2026-01-20 23:27:07.533797', 'SHARP', 1.0000, NULL, 4, 6, '13:00', '11:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (32, '2026-01-20 23:27:07.536579', '2026-01-20 23:27:07.536579', 'FLAT', 0.6000, NULL, 5, 6, '17:00', '13:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (33, '2026-01-20 23:27:07.538873', '2026-01-20 23:27:07.538873', 'PEAK', 0.8000, NULL, 6, 6, '19:00', '17:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (34, '2026-01-20 23:27:07.540501', '2026-01-20 23:27:07.540501', 'SHARP', 1.0000, NULL, 7, 6, '21:00', '19:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (35, '2026-01-20 23:27:07.542137', '2026-01-20 23:27:07.542137', 'FLAT', 0.6000, NULL, 8, 6, '23:00', '21:00');
INSERT INTO public.ems_price_policy_item (id, created_at, updated_at, period_type, price, remark, sort_order, policy_id, end_time, start_time) VALUES (36, '2026-01-20 23:27:07.543926', '2026-01-20 23:27:07.543926', 'VALLEY', 0.4000, NULL, 9, 6, '24:00', '23:00');


--
-- Data for Name: ems_product; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_product (id, created_at, updated_at, code, name, remark, sort_order, status, type, unit) VALUES (11, '2026-01-30 16:11:33.90909', '2026-01-30 16:11:33.90909', 'PROD_JM_001', '精密零件', '主要产品，用于汽车制造', 1, 0, '1', '件');
INSERT INTO public.ems_product (id, created_at, updated_at, code, name, remark, sort_order, status, type, unit) VALUES (12, '2026-01-30 16:11:33.90909', '2026-01-30 16:11:33.90909', 'PROD_JM_002', '传动轴', '高精度传动轴', 2, 0, '1', '件');
INSERT INTO public.ems_product (id, created_at, updated_at, code, name, remark, sort_order, status, type, unit) VALUES (13, '2026-01-30 16:11:33.90909', '2026-01-30 16:11:33.90909', 'PROD_JM_003', '粗钢板', '原材料', 3, 0, '3', '吨');
INSERT INTO public.ems_product (id, created_at, updated_at, code, name, remark, sort_order, status, type, unit) VALUES (14, '2026-01-30 16:11:33.912907', '2026-01-30 16:11:33.912907', 'PROD_ZP_001', '发电机组', '大型柴油发电机组', 4, 0, '1', '台');
INSERT INTO public.ems_product (id, created_at, updated_at, code, name, remark, sort_order, status, type, unit) VALUES (15, '2026-01-30 16:11:33.912907', '2026-01-30 16:11:33.912907', 'PROD_ZP_002', '控制柜', '电气控制柜', 5, 0, '1', '台');
INSERT INTO public.ems_product (id, created_at, updated_at, code, name, remark, sort_order, status, type, unit) VALUES (16, '2026-01-30 16:11:33.912907', '2026-01-30 16:11:33.912907', 'PROD_ZP_003', '水泥', '建筑原材料', 6, 0, '3', '吨');
INSERT INTO public.ems_product (id, created_at, updated_at, code, name, remark, sort_order, status, type, unit) VALUES (17, '2026-01-30 16:11:33.914223', '2026-01-30 16:11:33.914223', 'PROD_HG_001', '烘干件', '经过脱水处理的半成品', 7, 0, '2', 'kg');
INSERT INTO public.ems_product (id, created_at, updated_at, code, name, remark, sort_order, status, type, unit) VALUES (18, '2026-01-30 16:11:33.914223', '2026-01-30 16:11:33.914223', 'PROD_HG_002', '陶瓷胚体', '未烧制的陶瓷半成品', 8, 0, '2', '件');
INSERT INTO public.ems_product (id, created_at, updated_at, code, name, remark, sort_order, status, type, unit) VALUES (19, '2026-01-30 16:11:33.916389', '2026-01-30 16:11:33.916389', 'PROD_COMMON_001', 'CNC加工件', '通用数控加工零件', 9, 0, '1', '件');
INSERT INTO public.ems_product (id, created_at, updated_at, code, name, remark, sort_order, status, type, unit) VALUES (20, '2026-01-30 16:11:33.916389', '2026-01-30 16:11:33.916389', 'PROD_COMMON_002', '包装箱', '产品包装使用', 10, 0, '3', '个');


--
-- Data for Name: ems_production_record; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (1, '2026-01-30 00:35:48.207835', '2026-01-30 00:35:48.207835', 36, 'DAY', '精密零件', 1250.0000, '2026-01-05 00:00:00', '自动生成测试数据', '件', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (2, '2026-01-30 00:35:48.236641', '2026-01-30 00:35:48.236641', 36, 'DAY', '精密零件', 1320.5000, '2026-01-06 00:00:00', '自动生成测试数据', '件', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (3, '2026-01-30 00:35:48.238007', '2026-01-30 00:35:48.238007', 36, 'DAY', '精密零件', 1180.0000, '2026-01-07 00:00:00', '自动生成测试数据', '件', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (4, '2026-01-30 00:35:48.239422', '2026-01-30 00:35:48.239422', 36, 'DAY', '精密零件', 1405.2000, '2026-01-08 00:00:00', '自动生成测试数据', '件', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (5, '2026-01-30 00:35:48.240476', '2026-01-30 00:35:48.240476', 37, 'DAY', '总装成品', 450.0000, '2026-01-05 00:00:00', '自动生成测试数据', '台', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (6, '2026-01-30 00:35:48.243121', '2026-01-30 00:35:48.243121', 37, 'DAY', '总装成品', 485.0000, '2026-01-06 00:00:00', '自动生成测试数据', '台', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (7, '2026-01-30 00:35:48.244081', '2026-01-30 00:35:48.244081', 37, 'DAY', '总装成品', 420.0000, '2026-01-07 00:00:00', '自动生成测试数据', '台', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (8, '2026-01-30 00:35:48.245049', '2026-01-30 00:35:48.245049', 37, 'DAY', '总装成品', 510.0000, '2026-01-08 00:00:00', '自动生成测试数据', '台', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (9, '2026-01-30 00:35:48.246214', '2026-01-30 00:35:48.246214', 39, 'DAY', '烘干件', 2100.0000, '2026-01-05 00:00:00', '自动生成测试数据', 'kg', '1', '半成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (10, '2026-01-30 00:35:48.248286', '2026-01-30 00:35:48.248286', 39, 'DAY', '烘干件', 2250.0000, '2026-01-06 00:00:00', '自动生成测试数据', 'kg', '1', '半成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (11, '2026-01-30 00:35:48.250558', '2026-01-30 00:35:48.250558', 39, 'DAY', '烘干件', 1980.0000, '2026-01-07 00:00:00', '自动生成测试数据', 'kg', '1', '半成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (12, '2026-01-30 00:35:48.251709', '2026-01-30 00:35:48.251709', 39, 'DAY', '烘干件', 2400.0000, '2026-01-08 00:00:00', '自动生成测试数据', 'kg', '1', '半成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (13, '2026-01-30 00:35:48.252764', '2026-01-30 00:35:48.252764', 36, 'DAY', '粗钢', 85.5000, '2026-01-09 00:00:00', '自动生成测试数据', '吨', '1', '原材料');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (14, '2026-01-30 00:35:48.253544', '2026-01-30 00:35:48.253544', 36, 'DAY', '粗钢', 92.0000, '2026-01-10 00:00:00', '自动生成测试数据', '吨', '1', '原材料');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (15, '2026-01-30 00:35:48.255535', '2026-01-30 00:35:48.255535', 36, 'DAY', '粗钢', 78.5000, '2026-01-11 00:00:00', '自动生成测试数据', '吨', '1', '原材料');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (16, '2026-01-30 00:35:48.256607', '2026-01-30 00:35:48.256607', 37, 'DAY', '水泥', 320.0000, '2026-01-09 00:00:00', '自动生成测试数据', '吨', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (17, '2026-01-30 00:35:48.258186', '2026-01-30 00:35:48.258186', 37, 'DAY', '水泥', 345.5000, '2026-01-10 00:00:00', '自动生成测试数据', '吨', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (18, '2026-01-30 00:35:48.258936', '2026-01-30 00:35:48.258936', 45, 'DAY', 'CNC加工件', 850.0000, '2026-01-05 00:00:00', '自动生成测试数据', '件', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (19, '2026-01-30 00:35:48.260622', '2026-01-30 00:35:48.260622', 45, 'DAY', 'CNC加工件', 910.0000, '2026-01-06 00:00:00', '自动生成测试数据', '件', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (20, '2026-01-30 00:35:48.261418', '2026-01-30 00:35:48.261418', 45, 'DAY', 'CNC加工件', 880.0000, '2026-01-07 00:00:00', '自动生成测试数据', '件', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (21, '2026-01-31 12:46:20.557973', '2026-01-31 12:51:09.196242', 35, 'HOUR', '大AB', 8800.0000, '2026-01-01 16:00:00', NULL, '件', '1', '半成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (22, '2026-02-08 08:43:25.271357', '2026-02-08 08:43:25.271357', 35, 'HOUR', '大宝SOD蜜', 100.0000, '2026-02-08 10:00:00', '100瓶', '瓶', '1', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (23, '2026-02-08 08:44:00.53683', '2026-02-08 08:44:00.53683', 35, 'DAY', '电表', 210.0000, '2026-02-07 00:00:00', NULL, '度', '2', '成品');
INSERT INTO public.ems_production_record (id, created_at, updated_at, energy_unit_id, granularity, product_name, quantity, record_date, remark, unit, data_type, product_type) VALUES (24, '2026-02-08 08:44:39.46096', '2026-02-08 08:44:39.46096', 35, 'MONTH', '这这那', 1100.0000, '2026-01-01 00:00:00', NULL, '顿', '3', '千军万马');


--
-- Data for Name: ems_time_period_price; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: sys_config; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: sys_dept; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_dept (id, created_at, updated_at, ancestors, code, email, leader, name, phone, sort_order, status, parent_id, manager_id, description) VALUES (100, '2026-02-16 09:22:38.179264', '2026-02-16 09:22:38.179264', '0', 'TERRA', 'admin@terra.com', '张三', '泰若科技', '13800000001', 0, 0, NULL, NULL, NULL);
INSERT INTO public.sys_dept (id, created_at, updated_at, ancestors, code, email, leader, name, phone, sort_order, status, parent_id, manager_id, description) VALUES (202, '2026-02-19 16:45:04.90434', '2026-02-19 16:45:04.90434', NULL, NULL, NULL, NULL, '研发中心', NULL, 0, 0, 100, 97, '总部研发中心');
INSERT INTO public.sys_dept (id, created_at, updated_at, ancestors, code, email, leader, name, phone, sort_order, status, parent_id, manager_id, description) VALUES (101, '2026-02-16 09:22:38.179264', '2026-02-19 17:57:30.44432', 'null,100', 'SZ-TERRA', 'sz@terra.com', '李四', '深圳分公司', '13800000002', 1, 1, 100, NULL, NULL);
INSERT INTO public.sys_dept (id, created_at, updated_at, ancestors, code, email, leader, name, phone, sort_order, status, parent_id, manager_id, description) VALUES (103, '2026-02-16 09:22:38.179264', '2026-02-19 20:19:01.014779', 'null,100,101', 'R&D', 'rd@terra.com', '赵六', '研发部门', '13800000004', 1, 0, 101, 96, '深圳公司研发部门');
INSERT INTO public.sys_dept (id, created_at, updated_at, ancestors, code, email, leader, name, phone, sort_order, status, parent_id, manager_id, description) VALUES (205, '2026-02-19 20:19:41.451004', '2026-02-19 20:19:41.451004', '0,100', NULL, NULL, NULL, '测试中心', NULL, 0, 0, 100, 97, '全局测试中心');


--
-- Data for Name: sys_dict_data; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (1, '2026-01-16 23:29:55.931083', NULL, 'Y', '男', NULL, 1, '0', NULL, 'processing', 'SYS_USER_SEX', '0');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (2, '2026-01-16 23:29:55.931083', NULL, 'N', '女', NULL, 2, '0', NULL, 'success', 'SYS_USER_SEX', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (3, '2026-01-16 23:29:55.931083', NULL, 'N', '未知', NULL, 3, '0', NULL, 'default', 'SYS_USER_SEX', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (4, '2026-01-16 23:29:55.934998', NULL, 'Y', '显示', NULL, 1, '0', NULL, 'processing', 'SYS_SHOW_HIDE', '0');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (5, '2026-01-16 23:29:55.934998', NULL, 'N', '隐藏', NULL, 2, '0', NULL, 'error', 'SYS_SHOW_HIDE', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (6, '2026-01-16 23:29:55.935936', NULL, 'Y', '正常', NULL, 1, '0', NULL, 'processing', 'SYS_NORMAL_DISABLE', '0');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (7, '2026-01-16 23:29:55.935936', NULL, 'N', '停用', NULL, 2, '0', NULL, 'error', 'SYS_NORMAL_DISABLE', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (8, '2026-01-16 23:29:55.936777', NULL, 'Y', '正常', NULL, 1, '0', NULL, 'processing', 'SYS_JOB_STATUS', '0');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (9, '2026-01-16 23:29:55.936777', NULL, 'N', '暂停', NULL, 2, '0', NULL, 'error', 'SYS_JOB_STATUS', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (10, '2026-01-16 23:29:55.937818', NULL, 'Y', '默认', NULL, 1, '0', NULL, 'default', 'SYS_JOB_GROUP', 'DEFAULT');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (11, '2026-01-16 23:29:55.937818', NULL, 'N', '系统', NULL, 2, '0', NULL, 'processing', 'SYS_JOB_GROUP', 'SYSTEM');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (12, '2026-01-16 23:29:55.938778', NULL, 'Y', '是', NULL, 1, '0', NULL, 'processing', 'SYS_YES_NO', 'Y');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (13, '2026-01-16 23:29:55.938778', NULL, 'N', '否', NULL, 2, '0', NULL, 'error', 'SYS_YES_NO', 'N');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (14, '2026-01-16 23:29:55.93944', NULL, 'Y', '通知', NULL, 1, '0', NULL, 'warning', 'SYS_NOTICE_TYPE', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (15, '2026-01-16 23:29:55.93944', NULL, 'N', '公告', NULL, 2, '0', NULL, 'success', 'SYS_NOTICE_TYPE', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (16, '2026-01-16 23:29:55.940013', NULL, 'Y', '正常', NULL, 1, '0', NULL, 'processing', 'SYS_NOTICE_STATUS', '0');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (17, '2026-01-16 23:29:55.940013', NULL, 'N', '关闭', NULL, 2, '0', NULL, 'error', 'SYS_NOTICE_STATUS', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (18, '2026-01-16 23:29:55.940641', NULL, 'N', '新增', NULL, 1, '0', NULL, 'success', 'SYS_OPER_TYPE', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (19, '2026-01-16 23:29:55.940641', NULL, 'N', '修改', NULL, 2, '0', NULL, 'processing', 'SYS_OPER_TYPE', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (20, '2026-01-16 23:29:55.940641', NULL, 'N', '删除', NULL, 3, '0', NULL, 'error', 'SYS_OPER_TYPE', '3');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (21, '2026-01-16 23:29:55.940641', NULL, 'N', '授权', NULL, 4, '0', NULL, 'processing', 'SYS_OPER_TYPE', '4');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (22, '2026-01-16 23:29:55.940641', NULL, 'N', '导出', NULL, 5, '0', NULL, 'warning', 'SYS_OPER_TYPE', '5');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (23, '2026-01-16 23:29:55.940641', NULL, 'N', '导入', NULL, 6, '0', NULL, 'warning', 'SYS_OPER_TYPE', '6');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (24, '2026-01-16 23:29:55.940641', NULL, 'N', '强退', NULL, 7, '0', NULL, 'error', 'SYS_OPER_TYPE', '7');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (25, '2026-01-16 23:29:55.940641', NULL, 'N', '生成代码', NULL, 8, '0', NULL, 'warning', 'SYS_OPER_TYPE', '8');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (26, '2026-01-16 23:29:55.940641', NULL, 'N', '清空数据', NULL, 9, '0', NULL, 'error', 'SYS_OPER_TYPE', '9');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (27, '2026-01-16 23:29:55.941242', NULL, 'Y', '成功', NULL, 1, '0', NULL, 'success', 'SYS_COMMON_STATUS', '0');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (28, '2026-01-16 23:29:55.941242', NULL, 'N', '失败', NULL, 2, '0', NULL, 'error', 'SYS_COMMON_STATUS', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (29, '2026-01-16 23:29:55.94178', NULL, 'Y', '是', NULL, 1, '0', NULL, 'processing', 'SYS_IS_DEFAULT', 'Y');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (30, '2026-01-16 23:29:55.94178', NULL, 'N', '否', NULL, 2, '0', NULL, 'default', 'SYS_IS_DEFAULT', 'N');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (31, '2026-01-16 23:29:55.94232', NULL, 'Y', '启用', NULL, 1, '0', NULL, 'success', 'SYS_IS_ENABLE', 'Y');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (32, '2026-01-16 23:29:55.94232', NULL, 'N', '停用', NULL, 2, '0', NULL, 'error', 'SYS_IS_ENABLE', 'N');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (33, '2026-01-16 23:29:55.942869', NULL, 'N', '千瓦时', NULL, 1, '0', NULL, 'default', 'SYS_UNIT', 'kWh');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (34, '2026-01-16 23:29:55.942869', NULL, 'N', '千瓦', NULL, 2, '0', NULL, 'default', 'SYS_UNIT', 'kW');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (35, '2026-01-16 23:29:55.942869', NULL, 'N', '安培', NULL, 3, '0', NULL, 'default', 'SYS_UNIT', 'A');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (36, '2026-01-16 23:29:55.942869', NULL, 'N', '毫安', NULL, 4, '0', NULL, 'default', 'SYS_UNIT', 'mA');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (37, '2026-01-16 23:29:55.942869', NULL, 'N', '伏特', NULL, 5, '0', NULL, 'default', 'SYS_UNIT', 'V');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (38, '2026-01-16 23:29:55.942869', NULL, 'N', '千伏', NULL, 6, '0', NULL, 'default', 'SYS_UNIT', 'kV');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (39, '2026-01-16 23:29:55.942869', NULL, 'N', '千乏', NULL, 7, '0', NULL, 'default', 'SYS_UNIT', 'kVAR');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (40, '2026-01-16 23:29:55.942869', NULL, 'N', '伏安', NULL, 8, '0', NULL, 'default', 'SYS_UNIT', 'VA');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (41, '2026-01-16 23:29:55.942869', NULL, 'N', '赫兹', NULL, 9, '0', NULL, 'default', 'SYS_UNIT', 'Hz');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (42, '2026-01-16 23:29:55.942869', NULL, 'N', '立方米', NULL, 10, '0', NULL, 'default', 'SYS_UNIT', 'm³');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (43, '2026-01-16 23:29:55.942869', NULL, 'N', '立方米/小时', NULL, 11, '0', NULL, 'default', 'SYS_UNIT', 'm³/h');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (44, '2026-01-16 23:29:55.942869', NULL, 'N', '标准立方米', NULL, 12, '0', NULL, 'default', 'SYS_UNIT', 'Nm³');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (45, '2026-01-16 23:29:55.942869', NULL, 'N', '标准立方米/小时', NULL, 13, '0', NULL, 'default', 'SYS_UNIT', 'Nm³/h');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (46, '2026-01-16 23:29:55.942869', NULL, 'N', '吨', NULL, 14, '0', NULL, 'default', 'SYS_UNIT', 't');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (47, '2026-01-16 23:29:55.942869', NULL, 'N', '千克', NULL, 15, '0', NULL, 'default', 'SYS_UNIT', 'kg');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (48, '2026-01-16 23:29:55.942869', NULL, 'N', '吨/小时', NULL, 16, '0', NULL, 'default', 'SYS_UNIT', 't/h');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (49, '2026-01-16 23:29:55.942869', NULL, 'N', '千克/小时', NULL, 17, '0', NULL, 'default', 'SYS_UNIT', 'kg/h');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (50, '2026-01-16 23:29:55.942869', NULL, 'N', '千克/立方米', NULL, 18, '0', NULL, 'default', 'SYS_UNIT', 'kg/m³');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (51, '2026-01-16 23:29:55.942869', NULL, 'N', '兆帕', NULL, 19, '0', NULL, 'default', 'SYS_UNIT', 'MPa');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (52, '2026-01-16 23:29:55.942869', NULL, 'N', '千帕', NULL, 20, '0', NULL, 'default', 'SYS_UNIT', 'kPa');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (53, '2026-01-16 23:29:55.942869', NULL, 'N', '帕', NULL, 21, '0', NULL, 'default', 'SYS_UNIT', 'Pa');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (54, '2026-01-16 23:29:55.942869', NULL, 'N', '摄氏度', NULL, 22, '0', NULL, 'default', 'SYS_UNIT', '℃');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (55, '2026-01-16 23:29:55.942869', NULL, 'N', '华氏度', NULL, 23, '0', NULL, 'default', 'SYS_UNIT', '℉');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (56, '2026-01-16 23:29:55.942869', NULL, 'N', '相对湿度', NULL, 24, '0', NULL, 'default', 'SYS_UNIT', '%RH');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (57, '2026-01-16 23:29:55.942869', NULL, 'N', '百分比', NULL, 25, '0', NULL, 'default', 'SYS_UNIT', '%');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (58, '2026-01-16 23:29:55.942869', NULL, 'N', '元', NULL, 26, '0', NULL, 'default', 'SYS_UNIT', 'RMB');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (59, '2026-01-16 23:29:55.942869', NULL, 'N', '个', NULL, 27, '0', NULL, 'default', 'SYS_UNIT', 'N');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (60, '2026-01-16 23:29:55.942869', NULL, 'N', '小时', NULL, 28, '0', NULL, 'default', 'SYS_UNIT', 'hour');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (61, '2026-01-16 23:29:55.942869', NULL, 'N', '次', NULL, 29, '0', NULL, 'default', 'SYS_UNIT', 'times');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (62, '2026-01-16 23:29:55.942869', NULL, 'N', '吨标准煤', NULL, 30, '0', NULL, 'default', 'SYS_UNIT', 'tce');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (63, '2026-01-16 23:29:55.942869', NULL, 'N', '千克标准煤/吨', NULL, 31, '0', NULL, 'default', 'SYS_UNIT', 'kgce/t');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (64, '2026-01-16 23:29:55.944376', NULL, 'N', '尖', NULL, 1, '0', NULL, 'error', 'ELECTRICITY_PRICE', 'SHARP');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (65, '2026-01-16 23:29:55.944376', NULL, 'N', '峰', NULL, 2, '0', NULL, 'warning', 'ELECTRICITY_PRICE', 'PEAK');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (66, '2026-01-16 23:29:55.944376', NULL, 'N', '平', NULL, 3, '0', NULL, 'success', 'ELECTRICITY_PRICE', 'FLAT');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (67, '2026-01-16 23:29:55.944376', NULL, 'N', '谷', NULL, 4, '0', NULL, 'default', 'ELECTRICITY_PRICE', 'VALLEY');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (68, '2026-01-16 23:29:55.944376', NULL, 'N', '深谷', NULL, 5, '0', NULL, 'processing', 'ELECTRICITY_PRICE', 'DEEP');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (69, '2026-01-16 23:29:55.944868', NULL, 'Y', '能源类指标', NULL, 1, '0', NULL, 'processing', 'INDEX_CATEGORY', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (70, '2026-01-16 23:29:55.944868', NULL, 'N', '产品类指标', NULL, 2, '0', NULL, 'success', 'INDEX_CATEGORY', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (71, '2026-01-16 23:29:55.944868', NULL, 'N', '能效类指标', NULL, 3, '0', NULL, 'warning', 'INDEX_CATEGORY', '3');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (72, '2026-01-16 23:29:55.944868', NULL, 'N', '经营类指标', NULL, 4, '0', NULL, 'default', 'INDEX_CATEGORY', '4');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (73, '2026-01-16 23:29:55.944868', NULL, 'N', '其他', NULL, 5, '0', NULL, 'default', 'INDEX_CATEGORY', '5');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (74, '2026-01-16 23:29:55.945372', NULL, 'Y', '电表', NULL, 1, '0', NULL, 'processing', 'DEVICE_TYPE', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (75, '2026-01-16 23:29:55.945372', NULL, 'N', '水表', NULL, 2, '0', NULL, 'processing', 'DEVICE_TYPE', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (76, '2026-01-16 23:29:55.945372', NULL, 'N', '变频器', NULL, 3, '0', NULL, 'default', 'DEVICE_TYPE', '3');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (77, '2026-01-16 23:29:55.945372', NULL, 'N', '温湿度表', NULL, 4, '0', NULL, 'default', 'DEVICE_TYPE', '4');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (78, '2026-01-16 23:29:55.945372', NULL, 'N', '压力表', NULL, 5, '0', NULL, 'default', 'DEVICE_TYPE', '5');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (79, '2026-01-16 23:29:55.945372', NULL, 'N', '蒸汽表', NULL, 6, '0', NULL, 'default', 'DEVICE_TYPE', '6');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (80, '2026-01-16 23:29:55.945372', NULL, 'N', '氮气表', NULL, 7, '0', NULL, 'default', 'DEVICE_TYPE', '7');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (81, '2026-01-16 23:29:55.945372', NULL, 'N', '天然气表', NULL, 10, '0', NULL, 'default', 'DEVICE_TYPE', '10');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (82, '2026-01-16 23:29:55.947454', NULL, 'N', '使用中', NULL, 1, '0', NULL, 'success', 'METER_STATUS', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (83, '2026-01-16 23:29:55.947454', NULL, 'N', '检修中', NULL, 2, '0', NULL, 'warning', 'METER_STATUS', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (84, '2026-01-16 23:29:55.947454', NULL, 'Y', '备用', NULL, 3, '0', NULL, 'default', 'METER_STATUS', '3');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (85, '2026-01-16 23:29:55.947918', NULL, 'N', '实时数据', NULL, 1, '0', NULL, 'processing', 'DATA_TYPE', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (86, '2026-01-16 23:29:55.947918', NULL, 'N', '阶段数据', NULL, 2, '0', NULL, 'success', 'DATA_TYPE', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (87, '2026-01-16 23:29:55.948373', NULL, 'Y', '公司', NULL, 0, '0', NULL, 'processing', 'NODE_CATEGORY', '0');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (88, '2026-01-16 23:29:55.948373', NULL, 'N', '厂部', NULL, 1, '0', NULL, 'processing', 'NODE_CATEGORY', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (89, '2026-01-16 23:29:55.948373', NULL, 'N', '配电室', NULL, 2, '0', NULL, 'default', 'NODE_CATEGORY', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (90, '2026-01-16 23:29:55.948373', NULL, 'N', '区域', NULL, 3, '0', NULL, 'default', 'NODE_CATEGORY', '3');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (91, '2026-01-16 23:29:55.948373', NULL, 'N', '重点能耗设备', NULL, 4, '0', NULL, 'warning', 'NODE_CATEGORY', '4');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (92, '2026-01-16 23:29:55.948373', NULL, 'N', '设备', NULL, 5, '0', NULL, 'default', 'NODE_CATEGORY', '5');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (93, '2026-01-16 23:29:55.948373', NULL, 'N', '其他', NULL, 9, '0', NULL, 'default', 'NODE_CATEGORY', '9');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (94, '2026-01-16 23:29:55.948842', NULL, 'Y', '一级', NULL, 1, '0', NULL, 'success', 'FACILITY_GRADE', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (95, '2026-01-16 23:29:55.948842', NULL, 'N', '二级', NULL, 2, '0', NULL, 'processing', 'FACILITY_GRADE', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (96, '2026-01-16 23:29:55.948842', NULL, 'N', '三级', NULL, 3, '0', NULL, 'warning', 'FACILITY_GRADE', '3');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (97, '2026-01-16 23:29:55.948842', NULL, 'N', '无', NULL, 4, '0', NULL, 'default', 'FACILITY_GRADE', '4');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (98, '2026-01-16 23:29:55.949343', NULL, 'Y', '空压机', NULL, 1, '0', NULL, 'default', 'FACILITY_TYPE', '0');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (99, '2026-01-16 23:29:55.949343', NULL, 'N', '电机', NULL, 2, '0', NULL, 'default', 'FACILITY_TYPE', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (100, '2026-01-16 23:29:55.949343', NULL, 'N', '风机', NULL, 3, '0', NULL, 'default', 'FACILITY_TYPE', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (101, '2026-01-16 23:29:55.949343', NULL, 'N', '水泵', NULL, 4, '0', NULL, 'default', 'FACILITY_TYPE', '3');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (102, '2026-01-16 23:29:55.949343', NULL, 'N', '变压器', NULL, 5, '0', NULL, 'default', 'FACILITY_TYPE', '4');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (103, '2026-01-16 23:29:55.949343', NULL, 'N', '制冷机', NULL, 6, '0', NULL, 'default', 'FACILITY_TYPE', '5');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (104, '2026-01-16 23:29:55.949343', NULL, 'N', '锅炉', NULL, 7, '0', NULL, 'default', 'FACILITY_TYPE', '6');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (105, '2026-01-16 23:29:55.949343', NULL, 'N', '输送机', NULL, 8, '0', NULL, 'default', 'FACILITY_TYPE', '7');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (106, '2026-01-16 23:29:55.949343', NULL, 'N', '破碎机', NULL, 9, '0', NULL, 'default', 'FACILITY_TYPE', '8');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (107, '2026-01-16 23:29:55.949343', NULL, 'N', '其他', NULL, 10, '0', NULL, 'default', 'FACILITY_TYPE', '99');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (108, '2026-01-16 23:29:55.949937', NULL, 'Y', '指标配置', NULL, 1, '0', NULL, 'processing', 'MODEL_TYPE', 'index');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (109, '2026-01-16 23:29:55.949937', NULL, 'N', '仅节点', NULL, 2, '0', NULL, 'default', 'MODEL_TYPE', 'node');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (110, '2026-01-16 23:29:55.950363', NULL, 'Y', '实时', NULL, 0, '0', NULL, 'processing', 'WARN_TIME_SLOT', 'LIVE');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (111, '2026-01-16 23:29:55.950363', NULL, 'N', '小时', NULL, 1, '0', NULL, 'default', 'WARN_TIME_SLOT', 'HOUR');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (112, '2026-01-16 23:29:55.950363', NULL, 'N', '天', NULL, 2, '0', NULL, 'default', 'WARN_TIME_SLOT', 'DAY');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (113, '2026-01-16 23:29:55.950363', NULL, 'N', '月', NULL, 3, '0', NULL, 'default', 'WARN_TIME_SLOT', 'MONTH');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (114, '2026-01-16 23:29:55.950363', NULL, 'N', '年', NULL, 4, '0', NULL, 'default', 'WARN_TIME_SLOT', 'YEAR');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (115, '2026-01-16 23:29:55.950805', NULL, 'Y', '预警', NULL, 1, '0', NULL, 'warning', 'ALARM_TYPE', 'WARNING');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (116, '2026-01-16 23:29:55.950805', NULL, 'N', '报警', NULL, 2, '0', NULL, 'error', 'ALARM_TYPE', 'ALARM');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (117, '2026-01-16 23:29:55.951615', NULL, 'N', '高', NULL, 1, '0', NULL, 'error', 'ALARM_LEVEL', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (118, '2026-01-16 23:29:55.951615', NULL, 'N', '中', NULL, 2, '0', NULL, 'warning', 'ALARM_LEVEL', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (119, '2026-01-16 23:29:55.951615', NULL, 'Y', '低', NULL, 3, '0', NULL, 'default', 'ALARM_LEVEL', '3');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (120, '2026-01-16 23:29:55.952042', NULL, 'N', '上限', NULL, 1, '0', NULL, 'warning', 'LIMIT_TYPE', '1');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (121, '2026-01-16 23:29:55.952042', NULL, 'N', '下限', NULL, 2, '0', NULL, 'processing', 'LIMIT_TYPE', '2');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (122, '2026-01-16 23:29:55.952456', NULL, 'Y', '月', NULL, 1, '0', NULL, 'default', 'TIME_TYPE', 'MONTH');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (123, '2026-01-16 23:29:55.952456', NULL, 'N', '年', NULL, 2, '0', NULL, 'default', 'TIME_TYPE', 'YEAR');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (124, '2026-01-16 23:29:55.952909', NULL, 'N', '日', NULL, 1, '0', NULL, 'default', 'DATE_TYPE', 'DAY');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (125, '2026-01-16 23:29:55.952909', NULL, 'Y', '月', NULL, 2, '0', NULL, 'default', 'DATE_TYPE', 'MONTH');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (126, '2026-01-16 23:29:55.952909', NULL, 'N', '年', NULL, 3, '0', NULL, 'default', 'DATE_TYPE', 'YEAR');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (127, '2026-01-16 23:29:55.953352', NULL, 'N', '天', NULL, 1, '0', NULL, 'default', 'PERIOD', 'DAY');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (128, '2026-01-16 23:29:55.953352', NULL, 'Y', '月', NULL, 2, '0', NULL, 'default', 'PERIOD', 'MONTH');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (129, '2026-01-16 23:29:55.953352', NULL, 'N', '年', NULL, 3, '0', NULL, 'default', 'PERIOD', 'YEAR');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (130, '2026-01-16 23:29:55.953883', NULL, 'Y', '小时', NULL, 1, '0', NULL, 'default', 'ENTRY_DATA_TIME', 'HOUR');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (131, '2026-01-16 23:29:55.953883', NULL, 'N', '日', NULL, 2, '0', NULL, 'default', 'ENTRY_DATA_TIME', 'DAY');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (132, '2026-01-16 23:29:55.953883', NULL, 'N', '月', NULL, 3, '0', NULL, 'default', 'ENTRY_DATA_TIME', 'MONTH');
INSERT INTO public.sys_dict_data (id, created_at, updated_at, is_default, label, remark, sort_order, status, tag_color, tag_type, type_code, value) VALUES (133, '2026-01-16 23:29:55.953883', NULL, 'N', '年', NULL, 4, '0', NULL, 'default', 'ENTRY_DATA_TIME', 'YEAR');


--
-- Data for Name: sys_dict_type; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (1, '2026-01-16 23:29:55.925112', NULL, '用户性别', '用户性别列表', '0', 'SYS_USER_SEX');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (2, '2026-01-16 23:29:55.925112', NULL, '菜单状态', '菜单状态列表', '0', 'SYS_SHOW_HIDE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (3, '2026-01-16 23:29:55.925112', NULL, '系统开关', '系统开关列表', '0', 'SYS_NORMAL_DISABLE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (4, '2026-01-16 23:29:55.925112', NULL, '任务状态', '任务状态列表', '0', 'SYS_JOB_STATUS');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (5, '2026-01-16 23:29:55.925112', NULL, '任务分组', '任务分组列表', '0', 'SYS_JOB_GROUP');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (6, '2026-01-16 23:29:55.925112', NULL, '系统是否', '系统是否列表', '0', 'SYS_YES_NO');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (7, '2026-01-16 23:29:55.925112', NULL, '通知类型', '通知类型列表', '0', 'SYS_NOTICE_TYPE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (8, '2026-01-16 23:29:55.925112', NULL, '通知状态', '通知状态列表', '0', 'SYS_NOTICE_STATUS');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (9, '2026-01-16 23:29:55.925112', NULL, '操作类型', '操作类型列表', '0', 'SYS_OPER_TYPE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (10, '2026-01-16 23:29:55.925112', NULL, '系统状态', '登录状态列表', '0', 'SYS_COMMON_STATUS');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (11, '2026-01-16 23:29:55.925112', NULL, '单位管理', '单位基础数据', '0', 'SYS_UNIT');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (12, '2026-01-16 23:29:55.925112', NULL, '是否默认', '字典数据是否默认', '0', 'SYS_IS_DEFAULT');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (13, '2026-01-16 23:29:55.925112', NULL, '是否启用', '是否启用开关', '0', 'SYS_IS_ENABLE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (20, '2026-01-16 23:29:55.925112', NULL, '尖峰平谷配置', '电价类别配置', '0', 'ELECTRICITY_PRICE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (21, '2026-01-16 23:29:55.925112', NULL, '指标类型', '系统指标类型', '0', 'INDEX_CATEGORY');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (22, '2026-01-16 23:29:55.925112', NULL, '计量器具类型', '计量器具类型', '0', 'DEVICE_TYPE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (23, '2026-01-16 23:29:55.925112', NULL, '计量器具状态', '计量器具的使用状态', '0', 'METER_STATUS');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (24, '2026-01-16 23:29:55.925112', NULL, '参数数据类型', '标准参数配置数据类型', '0', 'DATA_TYPE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (25, '2026-01-16 23:29:55.925112', NULL, '指标节点类型', '指标节点分类', '0', 'NODE_CATEGORY');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (26, '2026-01-16 23:29:55.925112', NULL, '能耗等级', '设备档案的能耗等级', '0', 'FACILITY_GRADE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (27, '2026-01-16 23:29:55.925112', NULL, '设备类型', '设备档案管理的设备类型', '0', 'FACILITY_TYPE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (28, '2026-01-16 23:29:55.925112', NULL, '应用模型类型', '应用模型类型', '0', 'MODEL_TYPE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (30, '2026-01-16 23:29:55.925112', NULL, '预警报警时段', '预警报警时段类型', '0', 'WARN_TIME_SLOT');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (31, '2026-01-16 23:29:55.925112', NULL, '预警报警类型', '预警报警类型', '0', 'ALARM_TYPE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (32, '2026-01-16 23:29:55.925112', NULL, '报警级别', '报警级别', '0', 'ALARM_LEVEL');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (33, '2026-01-16 23:29:55.925112', NULL, '预报警限值类型', '预报警限值类型', '0', 'LIMIT_TYPE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (40, '2026-01-16 23:29:55.925112', NULL, '统计时间', '统计时间类型', '0', 'TIME_TYPE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (41, '2026-01-16 23:29:55.925112', NULL, '用能统计时间', '用能统计时间类型', '0', 'DATE_TYPE');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (42, '2026-01-16 23:29:55.925112', NULL, '期间', '数据录入期间类型', '0', 'PERIOD');
INSERT INTO public.sys_dict_type (id, created_at, updated_at, name, remark, status, type) VALUES (43, '2026-01-16 23:29:55.925112', NULL, '阶段数据录入时间', '阶段数据录入时间类型', '0', 'ENTRY_DATA_TIME');


--
-- Data for Name: sys_log; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: sys_login_log; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (1, '2026-02-10 20:57:31.854348', '2026-02-10 20:57:31.854348', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (2, '2026-02-10 20:57:36.173295', '2026-02-10 20:57:36.173295', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (3, '2026-02-10 23:21:05.5312', '2026-02-10 23:21:05.5312', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (4, '2026-02-10 23:28:27.648384', '2026-02-10 23:28:27.648384', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (5, '2026-02-10 23:28:44.200454', '2026-02-10 23:28:44.200454', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (6, '2026-02-11 09:11:04.326477', '2026-02-11 09:11:04.326477', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (7, '2026-02-11 10:55:32.03887', '2026-02-11 10:55:32.03887', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (8, '2026-02-11 12:45:39.461592', '2026-02-11 12:45:39.461592', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (9, '2026-02-11 14:03:42.084744', '2026-02-11 14:03:42.084744', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (10, '2026-02-11 17:34:12.075413', '2026-02-11 17:34:12.075413', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (11, '2026-02-11 20:52:51.56867', '2026-02-11 20:52:51.56867', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (12, '2026-02-12 09:31:03.506608', '2026-02-12 09:31:03.506608', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码已失效', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (13, '2026-02-12 09:31:19.895', '2026-02-12 09:31:19.895', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (14, '2026-02-15 08:26:50.980441', '2026-02-15 08:26:50.980441', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (15, '2026-02-15 23:48:30.458804', '2026-02-15 23:48:30.458804', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (16, '2026-02-15 23:48:38.686681', '2026-02-15 23:48:38.686681', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (17, '2026-02-16 07:57:56.873234', '2026-02-16 07:57:56.873234', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (18, '2026-02-16 08:28:20.341694', '2026-02-16 08:28:20.341694', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (19, '2026-02-16 09:07:29.495166', '2026-02-16 09:07:29.495166', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (20, '2026-02-16 12:20:53.668887', '2026-02-16 12:20:53.668887', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (21, '2026-02-16 17:04:30.474955', '2026-02-16 17:04:30.474955', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (22, '2026-02-16 18:30:25.35152', '2026-02-16 18:30:25.35152', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (23, '2026-02-16 21:04:40.168835', '2026-02-16 21:04:40.168835', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (24, '2026-02-16 22:50:43.982379', '2026-02-16 22:50:43.982379', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (25, '2026-02-17 08:15:33.68012', '2026-02-17 08:15:33.68012', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (26, '2026-02-17 09:11:02.528752', '2026-02-17 09:11:02.528752', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (27, '2026-02-17 09:11:06.495634', '2026-02-17 09:11:06.495634', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (28, '2026-02-17 10:06:05.832408', '2026-02-17 10:06:05.832408', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (29, '2026-02-17 10:06:10.335206', '2026-02-17 10:06:10.335206', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (30, '2026-02-17 13:25:31.36109', '2026-02-17 13:25:31.36109', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (31, '2026-02-17 16:33:53.648276', '2026-02-17 16:33:53.648276', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (32, '2026-02-17 17:11:04.565389', '2026-02-17 17:11:04.565389', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (33, '2026-02-17 19:20:06.772346', '2026-02-17 19:20:06.772346', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (34, '2026-02-17 20:31:25.453482', '2026-02-17 20:31:25.453482', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (35, '2026-02-17 21:18:40.726846', '2026-02-17 21:18:40.726846', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (36, '2026-02-17 21:18:47.095045', '2026-02-17 21:18:47.095045', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (37, '2026-02-17 21:20:20.56686', '2026-02-17 21:20:20.56686', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (38, '2026-02-17 21:36:06.175448', '2026-02-17 21:36:06.175448', 'Chrome', '127.0.0.1', '内网IP', NULL, '用户名或密码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (39, '2026-02-17 21:36:20.204013', '2026-02-17 21:36:20.204013', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (40, '2026-02-17 21:43:51.238441', '2026-02-17 21:43:51.238441', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'li.hang');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (41, '2026-02-17 21:44:09.264024', '2026-02-17 21:44:09.264024', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (42, '2026-02-17 21:44:14.249747', '2026-02-17 21:44:14.249747', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (43, '2026-02-17 21:59:02.57311', '2026-02-17 21:59:02.57311', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'li.hang');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (44, '2026-02-17 21:59:19.166565', '2026-02-17 21:59:19.166565', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (45, '2026-02-18 09:37:50.577189', '2026-02-18 09:37:50.577189', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码已失效', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (46, '2026-02-18 09:37:59.561647', '2026-02-18 09:37:59.561647', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (47, '2026-02-19 08:36:43.423744', '2026-02-19 08:36:43.423744', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (48, '2026-02-19 09:52:47.282955', '2026-02-19 09:52:47.282955', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (49, '2026-02-19 15:25:19.819972', '2026-02-19 15:25:19.819972', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (50, '2026-02-19 15:29:27.42883', '2026-02-19 15:29:27.42883', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (51, '2026-02-19 19:56:57.618783', '2026-02-19 19:56:57.618783', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码已失效', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (52, '2026-02-19 19:57:03.282453', '2026-02-19 19:57:03.282453', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (53, '2026-02-19 20:38:50.371523', '2026-02-19 20:38:50.371523', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (54, '2026-02-19 20:38:55.625133', '2026-02-19 20:38:55.625133', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (55, '2026-02-19 20:39:01.19193', '2026-02-19 20:39:01.19193', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (56, '2026-02-19 23:14:18.819749', '2026-02-19 23:14:18.819749', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (57, '2026-02-20 08:32:18.847397', '2026-02-20 08:32:18.847397', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (58, '2026-02-20 09:17:01.06448', '2026-02-20 09:17:01.06448', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (59, '2026-02-20 09:17:05.895816', '2026-02-20 09:17:05.895816', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (60, '2026-02-20 09:46:45.614759', '2026-02-20 09:46:45.614759', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (61, '2026-02-20 10:53:49.596902', '2026-02-20 10:53:49.596902', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (62, '2026-02-20 17:38:24.105843', '2026-02-20 17:38:24.105843', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (63, '2026-02-20 17:38:28.874763', '2026-02-20 17:38:28.874763', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (64, '2026-02-20 17:50:03.14754', '2026-02-20 17:50:03.14754', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (65, '2026-02-21 09:07:42.114876', '2026-02-21 09:07:42.114876', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (66, '2026-02-21 13:17:31.904537', '2026-02-21 13:17:31.904537', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (67, '2026-02-21 17:56:39.364655', '2026-02-21 17:56:39.364655', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (68, '2026-02-21 21:33:39.473633', '2026-02-21 21:33:39.473633', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (69, '2026-02-21 22:36:38.099545', '2026-02-21 22:36:38.099545', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (70, '2026-02-21 23:06:48.702298', '2026-02-21 23:06:48.702298', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (71, '2026-02-22 00:17:42.69689', '2026-02-22 00:17:42.69689', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (72, '2026-02-22 00:17:46.712741', '2026-02-22 00:17:46.712741', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (73, '2026-02-22 07:14:22.24298', '2026-02-22 07:14:22.24298', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (74, '2026-02-22 14:27:08.944128', '2026-02-22 14:27:08.944128', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (75, '2026-02-22 14:27:16.658806', '2026-02-22 14:27:16.658806', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (76, '2026-02-23 16:05:34.942705', '2026-02-23 16:05:34.942705', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (77, '2026-02-23 16:14:22.193365', '2026-02-23 16:14:22.193365', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (78, '2026-02-23 16:14:28.406156', '2026-02-23 16:14:28.406156', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (79, '2026-02-23 16:29:45.642084', '2026-02-23 16:29:45.642084', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (80, '2026-02-23 16:35:02.656218', '2026-02-23 16:35:02.656218', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (81, '2026-02-23 16:42:34.277662', '2026-02-23 16:42:34.277662', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (82, '2026-02-23 16:57:18.625398', '2026-02-23 16:57:18.625398', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (83, '2026-02-23 17:02:01.516917', '2026-02-23 17:02:01.516917', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (84, '2026-02-23 17:13:05.425128', '2026-02-23 17:13:05.425128', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (85, '2026-02-23 17:28:06.751839', '2026-02-23 17:28:06.751839', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (86, '2026-02-23 17:28:12.639473', '2026-02-23 17:28:12.639473', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (87, '2026-02-23 22:14:27.132984', '2026-02-23 22:14:27.132984', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (88, '2026-02-24 08:10:55.862512', '2026-02-24 08:10:55.862512', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (89, '2026-02-25 21:25:17.435765', '2026-02-25 21:25:17.435765', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (90, '2026-02-25 23:09:07.847061', '2026-02-25 23:09:07.847061', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (91, '2026-02-25 23:30:00.505054', '2026-02-25 23:30:00.505054', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码已失效', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (92, '2026-02-25 23:30:07.824484', '2026-02-25 23:30:07.824484', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (93, '2026-02-25 23:50:21.006666', '2026-02-25 23:50:21.006666', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (94, '2026-02-26 00:25:38.657948', '2026-02-26 00:25:38.657948', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (95, '2026-02-26 07:12:46.812176', '2026-02-26 07:12:46.812176', 'Chrome', '127.0.0.1', '内网IP', NULL, '验证码错误', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (96, '2026-02-26 07:12:50.89965', '2026-02-26 07:12:50.89965', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (97, '2026-02-26 08:23:45.424514', '2026-02-26 08:23:45.424514', 'Chrome', '127.0.0.1', '内网IP', NULL, '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (98, '2026-02-26 20:59:07.730377', '2026-02-26 20:59:07.730377', 'Chrome', '127.0.0.1', '内网IP', '2026-02-26 20:59:07.728153', '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (99, '2026-02-26 23:16:20.518835', '2026-02-26 23:16:20.518835', 'Chrome', '127.0.0.1', '内网IP', '2026-02-26 23:16:20.510475', '验证码已失效', 'OSX', '1', 'unknown');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (100, '2026-02-26 23:16:24.859472', '2026-02-26 23:16:24.859472', 'Chrome', '127.0.0.1', '内网IP', '2026-02-26 23:16:24.859136', '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (101, '2026-02-26 23:26:59.845761', '2026-02-26 23:26:59.845761', 'Chrome', '127.0.0.1', '内网IP', '2026-02-26 23:26:59.845405', '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (102, '2026-02-26 23:35:29.367526', '2026-02-26 23:35:29.367526', 'Chrome', '127.0.0.1', '内网IP', '2026-02-26 23:35:29.366915', '登录成功', 'OSX', '0', 'admin');
INSERT INTO public.sys_login_log (id, created_at, updated_at, browser, ipaddr, login_location, login_time, msg, os, status, user_name) VALUES (103, '2026-02-27 06:49:09.146319', '2026-02-27 06:49:09.146319', 'Chrome', '127.0.0.1', '内网IP', '2026-02-27 06:49:09.145763', '登录成功', 'OSX', '0', 'admin');


--
-- Data for Name: sys_menu; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: sys_module; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (24, '2026-02-21 22:35:43.619292', '2026-02-21 22:35:43.619292', 'MONITOR_CACHE', '缓存监控', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (25, '2026-02-21 22:35:43.632742', '2026-02-21 22:35:43.632742', 'SYSTEM_CONFIG', '参数配置', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (26, '2026-02-21 22:35:43.636869', '2026-02-21 22:35:43.636869', 'SYSTEM_DEPT', '部门管理', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (27, '2026-02-21 22:35:43.640474', '2026-02-21 22:35:43.640474', 'SYSTEM_LOG', '运行日志', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (28, '2026-02-21 22:35:43.642835', '2026-02-21 22:35:43.642835', 'SYSTEM_MENU', '菜单管理', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (29, '2026-02-21 22:35:43.645263', '2026-02-21 22:35:43.645263', 'SYSTEM_MODULE', '业务模块管理', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (30, '2026-02-21 22:35:43.648445', '2026-02-21 22:35:43.648445', 'SYSTEM_NOTICE', '通知公告', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (31, '2026-02-21 22:35:43.651077', '2026-02-21 22:35:43.651077', 'SYSTEM_PERMISSION', '权限管理', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (32, '2026-02-21 22:35:43.653534', '2026-02-21 22:35:43.653534', 'SYSTEM_ROLE', '角色管理', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (33, '2026-02-21 22:35:43.656753', '2026-02-21 22:35:43.656753', 'SYSTEM_USER', '用户管理', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (34, '2026-02-21 22:35:43.669095', '2026-02-21 22:35:43.669095', 'MONITOR_LOGININFOR', '登录日志', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (35, '2026-02-21 22:35:43.673134', '2026-02-21 22:35:43.673134', 'MONITOR_OPERLOG', '操作日志', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (36, '2026-02-21 22:35:43.676376', '2026-02-21 22:35:43.676376', 'EMS_ALARM-CONFIG', '预报警配置', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (37, '2026-02-21 22:35:43.680228', '2026-02-21 22:35:43.680228', 'EMS_ALARM-LIMIT-TYPE', '报警限值类型', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (38, '2026-02-21 22:35:43.684007', '2026-02-21 22:35:43.684007', 'EMS_ALARM-RECORD', '报警历史记录', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (39, '2026-02-21 22:35:43.686949', '2026-02-21 22:35:43.686949', 'EMS_BENCHMARK', '对标值管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (40, '2026-02-21 22:35:43.690151', '2026-02-21 22:35:43.690151', 'EMS_COST-POLICY-BINDING', '成本策略绑定管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (41, '2026-02-21 22:35:43.693161', '2026-02-21 22:35:43.693161', 'EMS_ENERGY-COST-RECORD', '能源成本记录管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (42, '2026-02-21 22:35:43.696889', '2026-02-21 22:35:43.696889', 'EMS_ENERGY-SAVING-PROJECT', '节能项目管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (43, '2026-02-21 22:35:43.700785', '2026-02-21 22:35:43.700785', 'EMS_ENERGY-TYPE', '能源类型管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (44, '2026-02-21 22:35:43.702811', '2026-02-21 22:35:43.702811', 'EMS_ENERGY-UNIT', '用能单元管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (45, '2026-02-21 22:35:43.704462', '2026-02-21 22:35:43.704462', 'EMS_KNOWLEDGE', '知识库管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (46, '2026-02-21 22:35:43.70647', '2026-02-21 22:35:43.70647', 'EMS_METER', '计量器具管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (47, '2026-02-21 22:35:43.708238', '2026-02-21 22:35:43.708238', 'EMS_METER-POINT', '采集点位管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (48, '2026-02-21 22:35:43.710154', '2026-02-21 22:35:43.710154', 'EMS_POLICY', '政策法规管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (49, '2026-02-21 22:35:43.712247', '2026-02-21 22:35:43.712247', 'EMS_PRICE-POLICY', '电价策略管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (50, '2026-02-21 22:35:43.714188', '2026-02-21 22:35:43.714188', 'EMS_PRODUCT', '产品管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (51, '2026-02-21 22:35:43.715557', '2026-02-21 22:35:43.715557', 'EMS_PRODUCTION-RECORD', '产品产量管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (52, '2026-02-21 22:35:43.717087', '2026-02-21 22:35:43.717087', 'EMS_TIME-PERIOD-PRICE', '分时电价配置管理', 20);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (53, '2026-02-21 22:35:43.718694', '2026-02-21 22:35:43.718694', 'SYSTEM_DICT', '字典数据', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (54, '2026-02-21 22:35:59.831286', '2026-02-21 22:35:59.831286', 'SYSTEM_POST', '岗位管理', 10);
INSERT INTO public.sys_module (id, created_at, updated_at, code, name, sort_order) VALUES (55, '2026-02-23 17:12:31.46745', '2026-02-23 17:12:31.46745', 'EMS_ENERGY-SAVING', '节能项目管理', 20);


--
-- Data for Name: sys_notice; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_notice (id, created_at, updated_at, notice_content, notice_title, notice_type, remark, status) VALUES (8, '2026-02-19 20:22:07.175657', '2026-02-19 20:22:07.175657', 32839, '关于2025年春节放假安排的通知', '1', '', 0);


--
-- Data for Name: sys_operation_log; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (11, '2026-02-16 17:15:00.207521', '2026-02-16 17:15:00.207521', 3, 562298, NULL, NULL, '{"timestamp":"2026-02-16 17:04:45","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.ems.controller.EnergyTypeController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '能源类型管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-16 17:14:10.445223', '/api/energy-types/28');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (12, '2026-02-16 23:18:28.630771', '2026-02-16 23:18:28.630771', 1, 47, NULL, '用户名 [曾永利] 已存在', NULL, 'com.terra.ems.admin.controller.SysUserController.create()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', '{"createdAt":null,"updatedAt":null,"id":null,"username":"曾永利","nickname":"lily","avatar":null,"email":"lily@qq.com","phone":null,"dept":null,"status":0,"accountExpireAt":null,"credentialsExpireAt":null,"failLoginCount":0,"lastLoginAt":null,"enabled":true,"credentialsNonExpired":true,"accountNonLocked":true,"accountNonExpired":true,"roles":[],"positions":[12],"deptId":100}', '2026-02-16 23:18:28.587741', '/api/system/user/create');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (13, '2026-02-16 23:21:46.072582', '2026-02-16 23:21:46.072582', 5, 1236, NULL, NULL, NULL, 'com.terra.ems.admin.controller.SysUserController.export()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-16 23:21:46.044627', '/api/system/user/export');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (14, '2026-02-16 23:22:13.273471', '2026-02-16 23:22:13.273471', 3, 74, NULL, NULL, '{"timestamp":"2026-02-16 23:22:13","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-16 23:22:13.253052', '/api/system/user/3');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (15, '2026-02-16 23:22:20.37199', '2026-02-16 23:22:20.37199', 3, 12, NULL, NULL, '{"timestamp":"2026-02-16 23:22:20","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-16 23:22:20.360906', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (16, '2026-02-17 15:45:49.875011', '2026-02-17 15:45:49.875011', 5, 1396, NULL, NULL, NULL, 'com.terra.ems.admin.controller.SysUserController.export()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 15:45:49.855022', '/api/system/user/export');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (17, '2026-02-17 15:46:20.054021', '2026-02-17 15:46:20.054021', 3, 71, NULL, NULL, '{"timestamp":"2026-02-17 15:46:20","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 15:46:20.039273', '/api/system/user/5');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (18, '2026-02-17 15:46:25.72732', '2026-02-17 15:46:25.72732', 3, 15, NULL, NULL, '{"timestamp":"2026-02-17 15:46:25","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 15:46:25.714469', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (19, '2026-02-17 16:25:52.670374', '2026-02-17 16:25:52.670374', 6, 612, NULL, 'Cannot invoke "com.terra.ems.system.entity.SysUser.getUsername()" because "user" is null', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 16:25:52.634852', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (20, '2026-02-17 16:29:54.18425', '2026-02-17 16:29:54.18425', 6, 374, NULL, NULL, '{"timestamp":"2026-02-17 16:29:54","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"恭喜您，数据已全部导入成功！共 0 条，数据如下：","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 16:29:54.156881', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (21, '2026-02-17 16:30:03.964706', '2026-02-17 16:30:03.964706', 6, 31, NULL, NULL, '{"timestamp":"2026-02-17 16:30:03","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"恭喜您，数据已全部导入成功！共 0 条，数据如下：","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 16:30:03.837055', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (22, '2026-02-17 16:34:31.177551', '2026-02-17 16:34:31.177551', 6, 21632, NULL, NULL, '{"timestamp":"2026-02-17 16:34:31","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"恭喜您，数据已全部导入成功！共 0 条，数据如下：","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 16:34:31.152298', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (23, '2026-02-17 16:36:19.536434', '2026-02-17 16:36:19.536434', 6, 34719, NULL, NULL, '{"timestamp":"2026-02-17 16:36:19","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"恭喜您，数据已全部导入成功！共 0 条，数据如下：","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 16:36:19.505508', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (24, '2026-02-17 16:45:08.017735', '2026-02-17 16:45:08.017735', 6, 5121, NULL, NULL, '{"timestamp":"2026-02-17 16:45:07","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"未检测到可导入的有效数据行，请检查文件内容及表头是否正确。","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 16:45:07.994913', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (25, '2026-02-17 16:45:30.936256', '2026-02-17 16:45:30.936256', 6, 34, NULL, NULL, '{"timestamp":"2026-02-17 16:45:30","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"未检测到可导入的有效数据行，请检查文件内容及表头是否正确。","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 16:45:30.919767', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (26, '2026-02-17 16:48:33.874173', '2026-02-17 16:48:33.874173', 6, 29, NULL, NULL, '{"timestamp":"2026-02-17 16:48:33","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"未检测到可导入的有效数据行，请检查文件内容及表头是否正确。","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 16:48:33.849598', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (27, '2026-02-17 16:50:38.1939', '2026-02-17 16:50:38.1939', 6, 33, NULL, 'Cannot invoke "Object.getClass()" because "obj" is null', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 16:50:38.173302', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (28, '2026-02-17 16:56:12.923348', '2026-02-17 16:56:12.923348', 6, 273, NULL, 'null object for private com.terra.ems.system.entity.SysDept com.terra.ems.system.entity.SysUser.dept', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 16:56:12.889674', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (29, '2026-02-17 17:11:14.311334', '2026-02-17 17:11:14.311334', 6, 60, NULL, 'null object for private com.terra.ems.system.entity.SysDept com.terra.ems.system.entity.SysUser.dept', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:11:14.276109', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (30, '2026-02-17 17:12:00.199394', '2026-02-17 17:12:00.199394', 6, 17005, NULL, 'null object for private com.terra.ems.system.entity.SysDept com.terra.ems.system.entity.SysUser.dept', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:12:00.180083', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (31, '2026-02-17 17:13:15.743825', '2026-02-17 17:13:15.743825', 6, 60928, NULL, 'null object for private com.terra.ems.system.entity.SysDept com.terra.ems.system.entity.SysUser.dept', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:13:15.726499', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (32, '2026-02-17 17:19:48.046145', '2026-02-17 17:19:48.046145', 6, 387996, NULL, 'null object for private com.terra.ems.system.entity.SysDept com.terra.ems.system.entity.SysUser.dept', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:19:48.013029', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (33, '2026-02-17 17:23:06.403832', '2026-02-17 17:23:06.403832', 6, 178288, NULL, 'null object for private com.terra.ems.system.entity.SysDept com.terra.ems.system.entity.SysUser.dept', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:23:06.368891', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (34, '2026-02-17 17:27:30.271467', '2026-02-17 17:27:30.271467', 6, 257967, NULL, 'null object for private com.terra.ems.system.entity.SysDept com.terra.ems.system.entity.SysUser.dept', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:27:30.243002', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (35, '2026-02-17 17:31:51.094961', '2026-02-17 17:31:51.094961', 6, 120042, NULL, 'null object for private com.terra.ems.system.entity.SysDept com.terra.ems.system.entity.SysUser.dept', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:31:51.074843', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (36, '2026-02-17 17:38:27.94409', '2026-02-17 17:38:27.94409', 6, 389616, NULL, '调用方法 [setGender] 失败', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:38:27.924133', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (37, '2026-02-17 17:56:32.332853', '2026-02-17 17:56:32.332853', 6, 1037519, NULL, '调用方法 [setGender] 失败', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:56:32.27539', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (38, '2026-02-17 17:57:33.435623', '2026-02-17 17:57:33.435623', 6, 298, NULL, '调用方法 [setGender] 失败', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:57:33.405729', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (39, '2026-02-17 17:58:49.316353', '2026-02-17 17:58:49.316353', 6, 333, NULL, '调用方法 [setGender] 失败', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 17:58:49.255616', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (40, '2026-02-17 18:04:59.170133', '2026-02-17 18:04:59.170133', 6, 391, NULL, '很抱歉，导入失败！共 10 条数据格式不正确，错误如下：<br/>1、账号 li.hang 导入失败：部门 [研发部] 在系统中不存在，请先创建该部门<br/>2、账号 chen.xiaoyu 导入失败：部门 [财务部] 在系统中不存在，请先创建该部门<br/>3、账号 wang.zihao 导入失败：部门 [运维中心] 在系统中不存在，请先创建该部门<br/>4、账号 zhang.lin 导入失败：部门 [人事部] 在系统中不存在，请先创建该部门<br/>5、账号 zhao.xu 导入失败：部门 [销售部] 在系统中不存在，请先创建该部门<br/>6、账号 liu.qian 导入失败：部门 [综合部] 在系统中不存在，请先创建该部门<br/>7、账号 zhou.kai 导入失败：部门 [研发部] 在系统中不存在，请先创建该部门<br/>8、账号 wu.meng 导入失败：部门 [质量部] 在系统中不存在，请先创建该部门<br/>9、账号 yang.fan 导入失败：部门 [研发部] 在系统中不存在，请先创建该部门<br/>10、账号 xu.ruo 导入失败：部门 [生产部] 在系统中不存在，请先创建该部门', NULL, 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 18:04:59.143944', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (41, '2026-02-17 19:20:30.64338', '2026-02-17 19:20:30.64338', 6, 2329, NULL, NULL, '{"timestamp":"2026-02-17 19:20:30","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"恭喜您，数据已全部导入成功！共 10 条。","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 19:20:30.629339', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (42, '2026-02-17 19:31:54.573899', '2026-02-17 19:31:54.573899', 3, 83, NULL, NULL, '{"timestamp":"2026-02-17 19:31:54","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 19:31:54.560434', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (43, '2026-02-17 19:32:07.739112', '2026-02-17 19:32:07.739112', 3, 27, NULL, NULL, '{"timestamp":"2026-02-17 19:32:07","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 19:32:07.72611', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (44, '2026-02-17 19:32:20.036244', '2026-02-17 19:32:20.036244', 3, 58, NULL, NULL, '{"timestamp":"2026-02-17 19:32:20","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 19:32:20.021902', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (45, '2026-02-17 19:33:28.134145', '2026-02-17 19:33:28.134145', 6, 1568, NULL, NULL, '{"timestamp":"2026-02-17 19:33:28","error":{"detail":"部分数据导入失败，请下载结果文档查看详情","message":null,"code":null,"field":null,"stackTrace":null},"code":"500","message":"部分数据导入失败，请下载结果文档查看详情","path":null,"data":null,"status":500,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 19:33:28.119429', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (46, '2026-02-17 19:34:07.051945', '2026-02-17 19:34:07.051945', 3, 22, NULL, NULL, '{"timestamp":"2026-02-17 19:34:07","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 19:34:07.038957', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (47, '2026-02-17 19:34:15.617914', '2026-02-17 19:34:15.617914', 3, 29, NULL, NULL, '{"timestamp":"2026-02-17 19:34:15","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 19:34:15.603341', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (48, '2026-02-17 19:34:26.497789', '2026-02-17 19:34:26.497789', 3, 34, NULL, NULL, '{"timestamp":"2026-02-17 19:34:26","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 19:34:26.482568', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (49, '2026-02-17 19:55:10.303969', '2026-02-17 19:55:10.303969', 6, 1476, NULL, NULL, '{"timestamp":"2026-02-17 19:55:10","error":{"detail":"部分数据导入失败，请查看结果文档","message":null,"code":null,"field":null,"stackTrace":null},"code":"500","message":"部分数据导入失败，请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":null,"failureCount":3},"status":500,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 19:55:10.289763', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (50, '2026-02-17 20:03:11.487208', '2026-02-17 20:03:11.487208', 3, 39, NULL, NULL, '{"timestamp":"2026-02-17 20:03:11","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 20:03:11.473291', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (51, '2026-02-17 20:03:20.043345', '2026-02-17 20:03:20.043345', 3, 24, NULL, NULL, '{"timestamp":"2026-02-17 20:03:20","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 20:03:20.030185', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (52, '2026-02-17 20:03:29.783955', '2026-02-17 20:03:29.783955', 3, 42, NULL, NULL, '{"timestamp":"2026-02-17 20:03:29","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 20:03:29.771359', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (53, '2026-02-17 20:03:39.348108', '2026-02-17 20:03:39.348108', 6, 1457, NULL, NULL, '{"timestamp":"2026-02-17 20:03:39","error":{"detail":"部分数据导入失败，请查看结果文档","message":null,"code":null,"field":null,"stackTrace":null},"code":"500","message":"部分数据导入失败，请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":null,"failureCount":3},"status":500,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 20:03:39.337115', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (54, '2026-02-17 20:15:27.038026', '2026-02-17 20:15:27.038026', 3, 21, NULL, NULL, '{"timestamp":"2026-02-17 20:15:27","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 20:15:27.023893', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (55, '2026-02-17 20:15:34.023219', '2026-02-17 20:15:34.023219', 3, 22, NULL, NULL, '{"timestamp":"2026-02-17 20:15:34","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 20:15:34.005549', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (56, '2026-02-17 20:15:40.242146', '2026-02-17 20:15:40.242146', 3, 50, NULL, NULL, '{"timestamp":"2026-02-17 20:15:40","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 20:15:40.228931', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (57, '2026-02-17 20:15:53.902487', '2026-02-17 20:15:53.902487', 6, 1450, NULL, NULL, '{"timestamp":"2026-02-17 20:15:53","error":{"detail":"部分数据导入失败，请查看结果文档","message":null,"code":null,"field":null,"stackTrace":null},"code":"500","message":"部分数据导入失败，请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":null,"failureCount":3},"status":500,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 20:15:53.889591', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (58, '2026-02-17 20:23:02.357156', '2026-02-17 20:23:02.357156', 3, 103, NULL, NULL, '{"timestamp":"2026-02-17 20:23:02","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 20:23:02.326289', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (59, '2026-02-17 20:23:14.297541', '2026-02-17 20:23:14.297541', 6, 2416, NULL, NULL, '{"timestamp":"2026-02-17 20:23:14","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"导入完成（存在失败记录），请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":null,"failureCount":3},"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 20:23:14.282931', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (60, '2026-02-17 20:24:51.660238', '2026-02-17 20:24:51.660238', 3, 77, NULL, NULL, '{"timestamp":"2026-02-17 20:24:51","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 20:24:51.645947', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (61, '2026-02-17 20:31:37.100989', '2026-02-17 20:31:37.100989', 6, 2034, NULL, NULL, '{"timestamp":"2026-02-17 20:31:37","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"导入完成（存在失败记录），请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":"2205ec9d-abc2-45c0-9fd5-93548d85ee68_用户导入结果.xlsx","failureCount":3},"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 20:31:37.074171', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (62, '2026-02-17 20:37:23.686383', '2026-02-17 20:37:23.686383', 3, 103, NULL, NULL, '{"timestamp":"2026-02-17 20:37:23","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 20:37:23.67233', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (63, '2026-02-17 20:37:58.814392', '2026-02-17 20:37:58.814392', 6, 2286, NULL, NULL, '{"timestamp":"2026-02-17 20:37:58","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"导入完成（存在失败记录），请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":"ad153a8a-52cf-4f97-8bea-6454127f7f8a_用户导入结果.xlsx","failureCount":3},"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 20:37:58.799124', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (64, '2026-02-17 21:11:59.108829', '2026-02-17 21:11:59.108829', 3, 117, NULL, NULL, '{"timestamp":"2026-02-17 21:11:59","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 21:11:59.083084', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (65, '2026-02-17 21:12:09.85095', '2026-02-17 21:12:09.85095', 6, 2326, NULL, NULL, '{"timestamp":"2026-02-17 21:12:09","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"导入完成（存在失败记录），请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":"ef8fa8a8-5247-42dc-a870-0f63403af4d2_用户导入结果.xlsx","failureCount":3},"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 21:12:09.83779', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (66, '2026-02-17 21:20:35.528461', '2026-02-17 21:20:35.528461', 3, 84, NULL, NULL, '{"timestamp":"2026-02-17 21:20:35","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 21:20:35.514437', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (67, '2026-02-17 21:20:44.006249', '2026-02-17 21:20:44.006249', 6, 2019, NULL, NULL, '{"timestamp":"2026-02-17 21:20:43","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"导入完成（存在失败记录），请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":"05503bf5-3101-4427-9248-964a64099371_用户导入结果.xlsx","failureCount":3},"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 21:20:43.993012', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (68, '2026-02-17 21:27:51.585442', '2026-02-17 21:27:51.585442', 3, 82, NULL, NULL, '{"timestamp":"2026-02-17 21:27:51","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 21:27:51.558015', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (69, '2026-02-17 21:28:04.912408', '2026-02-17 21:28:04.912408', 6, 2271, NULL, NULL, '{"timestamp":"2026-02-17 21:28:04","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"导入完成（存在失败记录），请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":"84648044-759c-48ae-a8ba-1936164380c9_用户导入结果.xlsx","failureCount":3},"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 21:28:04.899425', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (70, '2026-02-17 21:33:23.568162', '2026-02-17 21:33:23.568162', 3, 87, NULL, NULL, '{"timestamp":"2026-02-17 21:33:23","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 21:33:23.55148', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (71, '2026-02-17 21:33:31.331495', '2026-02-17 21:33:31.331495', 6, 1402, NULL, NULL, '{"timestamp":"2026-02-17 21:33:31","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"导入完成（存在失败记录），请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":"40ac0b38-8220-44e6-8a2d-d571e160a854_用户导入结果.xlsx","failureCount":3},"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 21:33:31.319339', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (72, '2026-02-17 21:42:32.226509', '2026-02-17 21:42:32.226509', 3, 68, NULL, NULL, '{"timestamp":"2026-02-17 21:42:32","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 21:42:32.20314', '/api/system/user');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (73, '2026-02-17 21:42:48.815568', '2026-02-17 21:42:48.815568', 6, 1734, NULL, NULL, '{"timestamp":"2026-02-17 21:42:48","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"导入完成（存在失败记录），请查看结果文档","path":null,"data":{"total":10,"successCount":7,"fileUrl":"e00ceac5-e642-43aa-b0d9-d8bf1108d5f6_用户导入结果.xlsx","failureCount":3},"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.importData()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', 'false', '2026-02-17 21:42:48.802666', '/api/system/user/importData');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (74, '2026-02-17 21:51:24.611779', '2026-02-17 21:51:24.611779', 2, 34, NULL, NULL, '{"timestamp":"2026-02-17 21:51:24","error":{"detail":"参数错误","message":null,"code":null,"field":null,"stackTrace":null},"code":"500","message":"参数错误","path":null,"data":null,"status":500,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.resetPwd()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '{"password":"SnowMan12#$"}', '2026-02-17 21:51:24.556771', '/api/system/user/resetPwd');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (75, '2026-02-17 21:51:42.989994', '2026-02-17 21:51:42.989994', 2, 3, NULL, NULL, '{"timestamp":"2026-02-17 21:51:42","error":{"detail":"参数错误","message":null,"code":null,"field":null,"stackTrace":null},"code":"500","message":"参数错误","path":null,"data":null,"status":500,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.resetPwd()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '{"password":"SnowMan12#$"}', '2026-02-17 21:51:42.977116', '/api/system/user/resetPwd');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (76, '2026-02-17 21:58:43.004991', '2026-02-17 21:58:43.004991', 2, 198, NULL, NULL, '{"timestamp":"2026-02-17 21:58:42","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"重置成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.resetPwd()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '{"userId":93,"password":"SnowMan12#$"}', '2026-02-17 21:58:42.970682', '/api/system/user/resetPwd');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (77, '2026-02-17 21:59:37.453244', '2026-02-17 21:59:37.453244', 3, 47, NULL, NULL, '{"timestamp":"2026-02-17 21:59:37","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '系统用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 21:59:37.439343', '/api/system/user/94');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (78, '2026-02-17 21:59:41.903456', '2026-02-17 21:59:41.903456', 5, 432, NULL, NULL, NULL, 'com.terra.ems.admin.controller.SysUserController.export()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-17 21:59:41.891966', '/api/system/user/export');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (79, '2026-02-19 10:13:30.100671', '2026-02-19 10:13:30.100671', 3, 74, NULL, 'could not execute statement [ERROR: update or delete on table "sys_post" violates foreign key constraint "fkng2mc7xcmyerevvobtw95bmu9" on table "sys_user_post"
  详细：Key (id)=(15) is still referenced from table "sys_user_post".] [delete from sys_post where id=?]; SQL [delete from sys_post where id=?]; constraint [fkng2mc7xcmyerevvobtw95bmu9]', NULL, 'com.terra.ems.admin.controller.SysPostController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 1, '岗位管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 10:13:30.08363', '/api/system/post/15');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (80, '2026-02-19 10:13:51.486193', '2026-02-19 10:13:51.486193', 3, 17, NULL, NULL, '{"timestamp":"2026-02-19 10:13:51","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPostController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '岗位管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 10:13:51.473672', '/api/system/post/19');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (81, '2026-02-19 10:15:34.933278', '2026-02-19 10:15:34.933278', 3, 21, NULL, NULL, '{"timestamp":"2026-02-19 10:15:34","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPostController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '岗位管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 10:15:34.919878', '/api/system/post');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (82, '2026-02-19 11:06:48.639408', '2026-02-19 11:06:48.639408', 2, 38, NULL, 'could not execute statement [ERROR: null value in column "name" of relation "sys_dept" violates not-null constraint
  详细：Failing row contains (103, 2026-02-16 09:22:38.179264, 2026-02-19 11:06:48.594959, null, null, null, null, null, null, 0, 0, 102).] [update sys_dept set ancestors=?,code=?,email=?,leader=?,name=?,parent_id=?,phone=?,sort_order=?,status=?,updated_at=? where id=?]; SQL [update sys_dept set ancestors=?,code=?,email=?,leader=?,name=?,parent_id=?,phone=?,sort_order=?,status=?,updated_at=? where id=?]; constraint [name" of relation "sys_dept]', NULL, 'com.terra.ems.admin.controller.SysDeptController.saveOrUpdate()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '{"createdAt":null,"updatedAt":null,"id":103,"parent":{"createdAt":null,"updatedAt":null,"id":102,"name":null,"code":null,"sortOrder":0,"leader":null,"phone":null,"email":null,"ancestors":null,"status":0,"parentId":null},"name":null,"code":null,"sortOrder":0,"leader":null,"phone":null,"email":null,"ancestors":null,"status":0,"children":[],"parentId":102}', '2026-02-19 11:06:48.622195', '/api/system/dept');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (83, '2026-02-19 16:40:53.903132', '2026-02-19 16:40:53.903132', 3, 75, NULL, 'could not execute statement [ERROR: update or delete on table "sys_dept" violates foreign key constraint "fkb3pkx0wbo6o8i8lj0gxr37v1n" on table "sys_user"
  详细：Key (id)=(104) is still referenced from table "sys_user".] [delete from sys_dept where id=?]; SQL [delete from sys_dept where id=?]; constraint [fkb3pkx0wbo6o8i8lj0gxr37v1n]', NULL, 'com.terra.ems.admin.controller.SysDeptController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 16:40:53.881728', '/api/system/dept/104');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (84, '2026-02-19 16:41:01.509153', '2026-02-19 16:41:01.509153', 3, 12, NULL, 'could not execute statement [ERROR: update or delete on table "sys_dept" violates foreign key constraint "fkb3pkx0wbo6o8i8lj0gxr37v1n" on table "sys_user"
  详细：Key (id)=(103) is still referenced from table "sys_user".] [delete from sys_dept where id=?]; SQL [delete from sys_dept where id=?]; constraint [fkb3pkx0wbo6o8i8lj0gxr37v1n]', NULL, 'com.terra.ems.admin.controller.SysDeptController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 16:41:01.495666', '/api/system/dept/103');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (85, '2026-02-19 16:41:05.193897', '2026-02-19 16:41:05.193897', 3, 16, NULL, NULL, '{"timestamp":"2026-02-19 16:41:05","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysDeptController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '部门管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 16:41:05.180494', '/api/system/dept/102');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (86, '2026-02-19 16:44:47.312629', '2026-02-19 16:44:47.312629', 3, 16, NULL, NULL, '{"timestamp":"2026-02-19 16:44:47","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysDeptController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '部门管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 16:44:47.299332', '/api/system/dept/201');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (87, '2026-02-19 16:58:29.287951', '2026-02-19 16:58:29.287951', 2, 48, NULL, 'could not execute statement [ERROR: null value in column "name" of relation "sys_dept" violates not-null constraint
  详细：Failing row contains (101, 2026-02-16 09:22:38.179264, 2026-02-19 16:58:29.26001, null, null, null, null, null, null, 0, 0, 202, null, null).] [update sys_dept set ancestors=?,code=?,description=?,email=?,leader=?,manager_id=?,name=?,parent_id=?,phone=?,sort_order=?,status=?,updated_at=? where id=?]; SQL [update sys_dept set ancestors=?,code=?,description=?,email=?,leader=?,manager_id=?,name=?,parent_id=?,phone=?,sort_order=?,status=?,updated_at=? where id=?]; constraint [name" of relation "sys_dept]', NULL, 'com.terra.ems.admin.controller.SysDeptController.saveOrUpdate()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '{"createdAt":null,"updatedAt":null,"id":101,"parent":{"createdAt":null,"updatedAt":null,"id":202,"name":null,"code":null,"sortOrder":0,"leader":null,"phone":null,"email":null,"ancestors":null,"status":0,"manager":null,"description":null,"memberCount":null,"managerId":null,"managerName":null,"parentId":null,"parentName":null},"name":null,"code":null,"sortOrder":0,"leader":null,"phone":null,"email":null,"ancestors":null,"status":0,"children":[],"manager":null,"description":null,"memberCount":null,"managerId":null,"managerName":null,"parentId":202,"parentName":null}', '2026-02-19 16:58:29.273489', '/api/system/dept');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (88, '2026-02-19 16:58:42.566265', '2026-02-19 16:58:42.566265', 2, 15, NULL, 'could not execute statement [ERROR: null value in column "name" of relation "sys_dept" violates not-null constraint
  详细：Failing row contains (101, 2026-02-16 09:22:38.179264, 2026-02-19 16:58:42.54882, null, null, null, null, null, null, 0, 0, 202, null, null).] [update sys_dept set ancestors=?,code=?,description=?,email=?,leader=?,manager_id=?,name=?,parent_id=?,phone=?,sort_order=?,status=?,updated_at=? where id=?]; SQL [update sys_dept set ancestors=?,code=?,description=?,email=?,leader=?,manager_id=?,name=?,parent_id=?,phone=?,sort_order=?,status=?,updated_at=? where id=?]; constraint [name" of relation "sys_dept]', NULL, 'com.terra.ems.admin.controller.SysDeptController.saveOrUpdate()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '{"createdAt":null,"updatedAt":null,"id":101,"parent":{"createdAt":null,"updatedAt":null,"id":202,"name":null,"code":null,"sortOrder":0,"leader":null,"phone":null,"email":null,"ancestors":null,"status":0,"manager":null,"description":null,"memberCount":null,"managerId":null,"managerName":null,"parentId":null,"parentName":null},"name":null,"code":null,"sortOrder":0,"leader":null,"phone":null,"email":null,"ancestors":null,"status":0,"children":[],"manager":null,"description":null,"memberCount":null,"managerId":null,"managerName":null,"parentId":202,"parentName":null}', '2026-02-19 16:58:42.553743', '/api/system/dept');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (89, '2026-02-19 17:03:14.173416', '2026-02-19 17:03:14.173416', 2, 91, NULL, 'could not execute statement [ERROR: update or delete on table "sys_dept" violates foreign key constraint "fkb3pkx0wbo6o8i8lj0gxr37v1n" on table "sys_user"
  详细：Key (id)=(103) is still referenced from table "sys_user".] [delete from sys_dept where id=?]; SQL [delete from sys_dept where id=?]; constraint [fkb3pkx0wbo6o8i8lj0gxr37v1n]', NULL, 'com.terra.ems.admin.controller.SysDeptController.saveOrUpdate()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '', '2026-02-19 17:03:14.15608', '/api/system/dept');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (90, '2026-02-19 17:03:22.569766', '2026-02-19 17:03:22.569766', 2, 18, NULL, 'could not execute statement [ERROR: update or delete on table "sys_dept" violates foreign key constraint "fkb3pkx0wbo6o8i8lj0gxr37v1n" on table "sys_user"
  详细：Key (id)=(103) is still referenced from table "sys_user".] [delete from sys_dept where id=?]; SQL [delete from sys_dept where id=?]; constraint [fkb3pkx0wbo6o8i8lj0gxr37v1n]', NULL, 'com.terra.ems.admin.controller.SysDeptController.saveOrUpdate()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '', '2026-02-19 17:03:22.557089', '/api/system/dept');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (91, '2026-02-19 17:03:30.708898', '2026-02-19 17:03:30.708898', 2, 24, NULL, 'could not execute statement [ERROR: update or delete on table "sys_dept" violates foreign key constraint "fkb3pkx0wbo6o8i8lj0gxr37v1n" on table "sys_user"
  详细：Key (id)=(103) is still referenced from table "sys_user".] [delete from sys_dept where id=?]; SQL [delete from sys_dept where id=?]; constraint [fkb3pkx0wbo6o8i8lj0gxr37v1n]', NULL, 'com.terra.ems.admin.controller.SysDeptController.saveOrUpdate()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '', '2026-02-19 17:03:30.695892', '/api/system/dept');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (92, '2026-02-19 17:11:56.811408', '2026-02-19 17:11:56.811408', 2, 54, NULL, 'could not execute statement [ERROR: update or delete on table "sys_dept" violates foreign key constraint "fkb3pkx0wbo6o8i8lj0gxr37v1n" on table "sys_user"
  详细：Key (id)=(103) is still referenced from table "sys_user".] [delete from sys_dept where id=?]; SQL [delete from sys_dept where id=?]; constraint [fkb3pkx0wbo6o8i8lj0gxr37v1n]', NULL, 'com.terra.ems.admin.controller.SysDeptController.saveOrUpdate()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '', '2026-02-19 17:11:56.796366', '/api/system/dept');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (93, '2026-02-19 18:21:42.888556', '2026-02-19 18:21:42.888556', 3, 67, NULL, 'could not execute statement [ERROR: update or delete on table "sys_dept" violates foreign key constraint "fkb3pkx0wbo6o8i8lj0gxr37v1n" on table "sys_user"
  详细：Key (id)=(202) is still referenced from table "sys_user".] [delete from sys_dept where id=?]; SQL [delete from sys_dept where id=?]; constraint [fkb3pkx0wbo6o8i8lj0gxr37v1n]', NULL, 'com.terra.ems.admin.controller.SysDeptController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 18:21:42.866122', '/api/system/dept/202');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (94, '2026-02-19 18:26:00.085819', '2026-02-19 18:26:00.085819', 3, 35, NULL, NULL, '{"timestamp":"2026-02-19 18:26:00","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysDeptController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '部门管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 18:26:00.069873', '/api/system/dept/104');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (95, '2026-02-19 19:58:30.673359', '2026-02-19 19:58:30.673359', 3, 28, NULL, NULL, '{"timestamp":"2026-02-19 19:58:30","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysDeptController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '部门管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 19:58:30.655932', '/api/system/dept/203');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (112, '2026-02-21 22:14:33.618821', '2026-02-21 22:14:33.618821', 3, 68, NULL, NULL, '{"timestamp":"2026-02-21 22:14:33","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:14:33.605177', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (113, '2026-02-21 22:14:37.867003', '2026-02-21 22:14:37.867003', 3, 77, NULL, NULL, '{"timestamp":"2026-02-21 22:14:37","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:14:37.854563', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (96, '2026-02-19 19:58:46.391671', '2026-02-19 19:58:46.391671', 2, 20, NULL, '部门不存在', NULL, 'com.terra.ems.admin.controller.SysDeptController.update()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'PUT', 1, '部门管理', '127.0.0.1', '内网IP', 'admin', '203 {"createdAt":null,"updatedAt":null,"id":203,"parent":{"createdAt":null,"updatedAt":null,"id":100,"name":null,"code":null,"sortOrder":0,"leader":null,"phone":null,"email":null,"ancestors":null,"status":0,"manager":null,"description":null,"memberCount":null,"managerId":null,"managerName":null,"parentId":null,"parentName":null},"name":"技术支撑中心","code":null,"sortOrder":0,"leader":null,"phone":null,"email":null,"ancestors":null,"status":0,"children":[],"manager":{"createdAt":null,"updatedAt":null,"id":1,"username":null,"realName":null,"avatar":null,"email":null,"phone":null,"gender":2,"employeeNo":null,"remark":null,"status":0,"accountExpireAt":null,"credentialsExpireAt":null,"failLoginCount":0,"lastLoginAt":null,"enabled":true,"accountNonLocked":true,"accountNonExpired":true,"credentialsNonExpired":true,"positions":null,"deptId":null},"description":null,"memberCount":null,"managerId":1,"managerName":null,"parentId":100,"parentName":null}', '2026-02-19 19:58:46.374764', '/api/system/dept/203');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (97, '2026-02-19 20:06:42.026929', '2026-02-19 20:06:42.026929', 3, 16, NULL, NULL, '{"timestamp":"2026-02-19 20:06:42","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysDeptController.delete()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '部门管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-19 20:06:42.008343', '/api/system/dept/204');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (98, '2026-02-21 21:49:01.351876', '2026-02-21 21:49:01.351876', 3, 74, NULL, NULL, '{"timestamp":"2026-02-21 21:49:01","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 21:49:01.333612', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (99, '2026-02-21 21:49:05.970867', '2026-02-21 21:49:05.970867', 3, 35, NULL, NULL, '{"timestamp":"2026-02-21 21:49:05","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 21:49:05.958078', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (100, '2026-02-21 21:49:13.037919', '2026-02-21 21:49:13.037919', 3, 73, NULL, NULL, '{"timestamp":"2026-02-21 21:49:13","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 21:49:13.022662', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (101, '2026-02-21 21:49:18.947948', '2026-02-21 21:49:18.947948', 3, 69, NULL, NULL, '{"timestamp":"2026-02-21 21:49:18","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 21:49:18.93527', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (102, '2026-02-21 21:53:06.580722', '2026-02-21 21:53:06.580722', 3, 71, NULL, NULL, '{"timestamp":"2026-02-21 21:53:06","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 21:53:06.564049', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (103, '2026-02-21 21:53:10.696135', '2026-02-21 21:53:10.696135', 3, 57, NULL, NULL, '{"timestamp":"2026-02-21 21:53:10","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 21:53:10.684433', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (104, '2026-02-21 21:53:14.649545', '2026-02-21 21:53:14.649545', 3, 63, NULL, NULL, '{"timestamp":"2026-02-21 21:53:14","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 21:53:14.63742', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (105, '2026-02-21 21:53:18.988868', '2026-02-21 21:53:18.988868', 3, 48, NULL, NULL, '{"timestamp":"2026-02-21 21:53:18","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 21:53:18.97727', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (106, '2026-02-21 22:13:27.300534', '2026-02-21 22:13:27.300534', 3, 79, NULL, NULL, '{"timestamp":"2026-02-21 22:13:27","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:13:27.281892', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (107, '2026-02-21 22:13:31.619672', '2026-02-21 22:13:31.619672', 3, 79, NULL, NULL, '{"timestamp":"2026-02-21 22:13:31","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:13:31.565508', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (108, '2026-02-21 22:13:35.535009', '2026-02-21 22:13:35.535009', 3, 55, NULL, NULL, '{"timestamp":"2026-02-21 22:13:35","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:13:35.522356', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (109, '2026-02-21 22:13:40.164311', '2026-02-21 22:13:40.164311', 3, 71, NULL, NULL, '{"timestamp":"2026-02-21 22:13:40","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:13:40.149189', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (110, '2026-02-21 22:14:25.243746', '2026-02-21 22:14:25.243746', 3, 48, NULL, NULL, '{"timestamp":"2026-02-21 22:14:25","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:14:25.230982', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (111, '2026-02-21 22:14:29.068907', '2026-02-21 22:14:29.068907', 3, 68, NULL, NULL, '{"timestamp":"2026-02-21 22:14:29","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:14:29.055505', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (114, '2026-02-21 22:14:48.460926', '2026-02-21 22:14:48.460926', 3, 37, NULL, NULL, '{"timestamp":"2026-02-21 22:14:48","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:14:48.448678', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (115, '2026-02-21 22:14:52.087318', '2026-02-21 22:14:52.087318', 3, 62, NULL, NULL, '{"timestamp":"2026-02-21 22:14:52","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:14:52.074776', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (116, '2026-02-21 22:14:55.294709', '2026-02-21 22:14:55.294709', 3, 69, NULL, NULL, '{"timestamp":"2026-02-21 22:14:55","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:14:55.281676', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (117, '2026-02-21 22:14:58.872401', '2026-02-21 22:14:58.872401', 3, 47, NULL, NULL, '{"timestamp":"2026-02-21 22:14:58","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"删除成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysPermissionController.deleteBatch()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '权限管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-21 22:14:58.858092', '/api/system/permission');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (118, '2026-02-22 07:54:54.464331', '2026-02-22 07:54:54.464331', 2, 202, NULL, NULL, '{"timestamp":"2026-02-22 07:54:54","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"更新权限成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysRoleController.updatePermissions()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '角色管理', '127.0.0.1', '内网IP', 'admin', '1 {"permissionIds":[401,402,403,406,404,405,407,408,410,409,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,437,438,439,436,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,472,471,473,474,475,476,477,478,479,480,431,432,433,434,435]}', '2026-02-22 07:54:54.442891', '/api/system/role/1/permissions');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (119, '2026-02-22 07:55:21.386804', '2026-02-22 07:55:21.386804', 2, 112, NULL, NULL, '{"timestamp":"2026-02-22 07:55:21","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"更新权限成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysRoleController.updatePermissions()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '角色管理', '127.0.0.1', '内网IP', 'admin', '1 {"permissionIds":[401,402,403,406,404,405,407,408,410,409,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,437,438,439,436,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,472,471,473,474,475,476,477,478,479,480,431,432,433,434,435]}', '2026-02-22 07:55:21.37405', '/api/system/role/1/permissions');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (120, '2026-02-22 08:00:53.308657', '2026-02-22 08:00:53.308657', 2, 130, NULL, NULL, '{"timestamp":"2026-02-22 08:00:53","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"更新权限成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysRoleController.updatePermissions()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '角色管理', '127.0.0.1', '内网IP', 'admin', '1 {"permissionIds":[401,402,403,406,404,405,407,408,410,409,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,437,438,439,436,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,472,471,473,474,475,476,477,478,479,480,431,432,433,434,435]}', '2026-02-22 08:00:53.293768', '/api/system/role/1/permissions');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (121, '2026-02-22 08:54:51.815088', '2026-02-22 08:54:51.815088', 2, 190, NULL, NULL, '{"timestamp":"2026-02-22 08:54:51","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"移除成员成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysRoleController.removeMembers()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '角色管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-22 08:54:51.78782', '/api/system/role/1/members');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (122, '2026-02-22 08:54:58.9539', '2026-02-22 08:54:58.9539', 2, 61, NULL, NULL, '{"timestamp":"2026-02-22 08:54:58","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"移除成员成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysRoleController.removeMembers()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '角色管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-22 08:54:58.939993', '/api/system/role/1/members');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (123, '2026-02-22 09:13:18.344138', '2026-02-22 09:13:18.344138', 2, 102, NULL, NULL, '{"timestamp":"2026-02-22 09:13:18","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"添加成员成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysRoleController.addMembers()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '角色管理', '127.0.0.1', '内网IP', 'admin', '1 [96,99]', '2026-02-22 09:13:18.325891', '/api/system/role/1/members');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (124, '2026-02-22 09:20:45.365714', '2026-02-22 09:20:45.365714', 2, 105, NULL, NULL, '{"timestamp":"2026-02-22 09:20:45","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"添加成员成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysRoleController.addMembers()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '角色管理', '127.0.0.1', '内网IP', 'admin', '1 [96,99,95]', '2026-02-22 09:20:45.348859', '/api/system/role/1/members');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (125, '2026-02-22 09:20:52.668318', '2026-02-22 09:20:52.668318', 2, 84, NULL, NULL, '{"timestamp":"2026-02-22 09:20:52","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"移除成员成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysRoleController.removeMembers()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'DELETE', 0, '角色管理', '127.0.0.1', '内网IP', 'admin', '{}', '2026-02-22 09:20:52.655242', '/api/system/role/1/members');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (126, '2026-02-22 09:21:00.33483', '2026-02-22 09:21:00.33483', 2, 162, NULL, NULL, '{"timestamp":"2026-02-22 09:21:00","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"更新权限成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysRoleController.updatePermissions()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '角色管理', '127.0.0.1', '内网IP', 'admin', '1 {"permissionIds":[401,402,403,404,405,406,407,408,410,409,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,426,427,428,429,430,437,438,439,436,440,441,442,443,444,445,446,447,448,449,450,451,452,453,454,455,456,457,458,459,460,461,462,463,464,465,466,467,468,469,470,471,472,473,474,475,476,477,478,479,480]}', '2026-02-22 09:21:00.32224', '/api/system/role/1/permissions');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (127, '2026-02-23 19:34:02.952417', '2026-02-23 19:34:02.952417', 2, 124, NULL, NULL, '{"timestamp":"2026-02-23 19:34:02","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"设置成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.setSuper()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '97 {"isSuper":true}', '2026-02-23 19:34:02.930911', '/api/system/user/97/setSuper');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (128, '2026-02-25 21:47:46.727513', '2026-02-25 21:47:46.727513', 2, 151, NULL, NULL, '{"timestamp":"2026-02-25 21:47:46","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"分配成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.updateRoles()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '98 [1]', '2026-02-25 21:47:46.713825', '/api/system/user/98/roles');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (129, '2026-02-25 21:48:08.644572', '2026-02-25 21:48:08.644572', 2, 34, NULL, NULL, '{"timestamp":"2026-02-25 21:48:08","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"分配成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.updateRoles()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '98 [1]', '2026-02-25 21:48:08.632353', '/api/system/user/98/roles');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (130, '2026-02-25 21:48:14.040957', '2026-02-25 21:48:14.040957', 2, 17, NULL, NULL, '{"timestamp":"2026-02-25 21:48:14","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"分配成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.updateRoles()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '98 []', '2026-02-25 21:48:14.027432', '/api/system/user/98/roles');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (131, '2026-02-25 21:48:15.47508', '2026-02-25 21:48:15.47508', 2, 30, NULL, NULL, '{"timestamp":"2026-02-25 21:48:15","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"分配成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.updateRoles()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '98 []', '2026-02-25 21:48:15.460301', '/api/system/user/98/roles');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (132, '2026-02-25 21:48:25.665', '2026-02-25 21:48:25.665', 2, 39, NULL, NULL, '{"timestamp":"2026-02-25 21:48:25","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"分配成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.updateRoles()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '98 [1]', '2026-02-25 21:48:25.652032', '/api/system/user/98/roles');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (133, '2026-02-25 21:52:08.567401', '2026-02-25 21:52:08.567401', 2, 17, NULL, NULL, '{"timestamp":"2026-02-25 21:52:08","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"分配成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.updateRoles()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '98 []', '2026-02-25 21:52:08.552665', '/api/system/user/98/roles');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (134, '2026-02-25 21:52:19.708411', '2026-02-25 21:52:19.708411', 2, 48, NULL, NULL, '{"timestamp":"2026-02-25 21:52:19","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"分配成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.updateRoles()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '95 [1]', '2026-02-25 21:52:19.695853', '/api/system/user/95/roles');
INSERT INTO public.sys_operation_log (id, created_at, updated_at, business_type, cost_time, dept_name, error_msg, json_result, method, oper_ip, oper_location, oper_name, oper_param, oper_time, oper_url, operator_type, request_method, status, title, operation_ip, operation_location, operation_name, operation_param, operation_time, operation_url) VALUES (135, '2026-02-25 21:52:54.944049', '2026-02-25 21:52:54.944049', 2, 48, NULL, NULL, '{"timestamp":"2026-02-25 21:52:54","error":{"detail":null,"message":null,"code":null,"field":null,"stackTrace":null},"code":"20000","message":"分配成功","path":null,"data":null,"status":200,"traceId":null}', 'com.terra.ems.admin.controller.SysUserController.updateRoles()', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'POST', 0, '用户管理', '127.0.0.1', '内网IP', 'admin', '93 [1]', '2026-02-25 21:52:54.926993', '/api/system/user/93/roles');


--
-- Data for Name: sys_permission; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (436, '2026-02-21 22:15:23.346915', '2026-02-22 15:02:17.341362', 'system:role:edit', NULL, '移除角色成员', true, 32);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (441, '2026-02-21 22:15:23.352864', '2026-02-22 20:36:21.267689', 'system:user:remove', NULL, '批量删除用户', false, 33);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (445, '2026-02-21 22:15:23.357741', '2026-02-22 20:36:21.268889', 'system:user:resetPwd', NULL, '重置用户密码', false, 33);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (446, '2026-02-21 22:15:23.359885', '2026-02-22 20:36:21.268953', 'system:user:import', NULL, '导入用户数据', false, 33);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (498, '2026-02-23 18:36:44.0985', '2026-02-23 18:36:44.0985', 'ems:alarm-limit-type:query', NULL, '按ID查询', false, 37);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (403, '2026-02-21 22:15:23.280408', '2026-02-21 22:35:43.766692', 'system:config:edit', NULL, '修改参数', false, 25);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (431, '2026-02-21 22:15:23.34192', '2026-02-21 22:35:59.876603', 'system:post:edit', NULL, '修改岗位', false, 54);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (432, '2026-02-21 22:15:23.342953', '2026-02-21 22:35:59.877533', 'system:post:remove', NULL, '批量删除岗位', false, 54);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (433, '2026-02-21 22:15:23.34392', '2026-02-21 22:35:59.877591', 'system:post:export', NULL, '导出岗位数据', false, 54);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (434, '2026-02-21 22:15:23.344858', '2026-02-21 22:35:59.87764', 'system:post:list', NULL, '查询岗位列表', false, 54);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (435, '2026-02-21 22:15:23.345955', '2026-02-21 22:35:59.877693', 'system:post:query', NULL, '查询岗位详情', false, 54);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (401, '2026-02-21 22:15:23.252631', '2026-02-21 22:35:43.764882', 'monitor:cache:list', NULL, '获取缓存指定键值', false, 24);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (402, '2026-02-21 22:15:23.277666', '2026-02-21 22:35:43.766637', 'monitor:cache:remove', NULL, '清空所有缓存', false, 24);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (405, '2026-02-21 22:15:23.283052', '2026-02-21 22:35:43.766771', 'system:config:list', NULL, '查询参数列表', false, 25);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (408, '2026-02-21 22:15:23.290346', '2026-02-21 22:35:43.766877', 'system:dept:remove', NULL, '批量删除部门', false, 26);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (410, '2026-02-21 22:15:23.298665', '2026-02-21 22:35:43.76695', 'system:dept:query', NULL, '查询部门详情', false, 26);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (411, '2026-02-21 22:15:23.302749', '2026-02-21 22:35:43.766979', 'system:log:remove', NULL, '批量删除', false, 27);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (412, '2026-02-21 22:15:23.306164', '2026-02-21 22:35:43.767007', 'system:log:list', NULL, '分页查询', false, 27);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (413, '2026-02-21 22:15:23.309448', '2026-02-21 22:35:43.767037', 'system:log:query', NULL, '详情', false, 27);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (414, '2026-02-21 22:15:23.312984', '2026-02-21 22:35:43.767074', 'system:menu:edit', NULL, '修改菜单', false, 28);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (416, '2026-02-21 22:15:23.317448', '2026-02-21 22:35:43.767138', 'system:menu:list', NULL, '查询菜单树', false, 28);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (417, '2026-02-21 22:15:23.320433', '2026-02-21 22:35:43.767166', 'system:menu:query', NULL, '查询菜单详情', false, 28);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (418, '2026-02-21 22:15:23.323942', '2026-02-21 22:35:43.76731', 'system:module:edit', NULL, '修改模块', false, 29);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (419, '2026-02-21 22:15:23.325182', '2026-02-21 22:35:43.767356', 'system:module:remove', NULL, '批量删除模块', false, 29);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (420, '2026-02-21 22:15:23.326434', '2026-02-21 22:35:43.767387', 'system:module:list', NULL, '查询模块列表', false, 29);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (421, '2026-02-21 22:15:23.327713', '2026-02-21 22:35:43.767419', 'system:module:query', NULL, '查询模块详情', false, 29);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (422, '2026-02-21 22:15:23.329264', '2026-02-21 22:35:43.767446', 'system:notice:edit', NULL, '修改公告', false, 30);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (423, '2026-02-21 22:15:23.331359', '2026-02-21 22:35:43.767474', 'system:notice:remove', NULL, '批量删除公告', false, 30);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (425, '2026-02-21 22:15:23.33491', '2026-02-21 22:35:43.767529', 'system:notice:query', NULL, '查询公告详情', false, 30);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (426, '2026-02-21 22:15:23.336353', '2026-02-21 22:35:43.767555', 'system:permission:edit', NULL, '修改权限', false, 31);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (429, '2026-02-21 22:15:23.339829', '2026-02-21 22:35:43.767634', 'system:permission:list', NULL, '查询权限列表', false, 31);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (430, '2026-02-21 22:15:23.340844', '2026-02-21 22:35:43.767662', 'system:permission:query', NULL, '查询权限详情', false, 31);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (438, '2026-02-21 22:15:23.349311', '2026-02-21 22:35:43.76774', 'system:role:list', NULL, '查询角色列表', false, 32);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (439, '2026-02-21 22:15:23.350606', '2026-02-21 22:35:43.767767', 'system:role:query', NULL, '查询角色详情', false, 32);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (442, '2026-02-21 22:15:23.353927', '2026-02-21 22:35:43.767864', 'system:user:add', NULL, '新增用户(带关联)', false, 33);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (443, '2026-02-21 22:15:23.355202', '2026-02-21 22:35:43.767891', 'system:user:export', NULL, '导出导入模板', false, 33);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (444, '2026-02-21 22:15:23.356707', '2026-02-21 22:35:43.767923', 'system:user:list', NULL, '查询用户列表', false, 33);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (447, '2026-02-21 22:15:23.360963', '2026-02-21 22:35:43.768005', 'system:user:query', NULL, '查询用户详情', false, 33);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (448, '2026-02-21 22:15:23.362163', '2026-02-21 22:35:43.768041', 'monitor:logininfor:remove', NULL, '批量删除登录日志', false, 34);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (449, '2026-02-21 22:15:23.363531', '2026-02-21 22:35:43.768067', 'monitor:logininfor:list', NULL, '查询登录日志列表', false, 34);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (450, '2026-02-21 22:15:23.364884', '2026-02-21 22:35:43.768092', 'monitor:logininfor:query', NULL, '查询登录日志详情', false, 34);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (451, '2026-02-21 22:15:23.366121', '2026-02-21 22:35:43.768117', 'monitor:operlog:remove', NULL, '批量删除操作日志', false, 35);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (452, '2026-02-21 22:15:23.367194', '2026-02-21 22:35:43.768143', 'monitor:operlog:list', NULL, '查询操作日志列表', false, 35);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (453, '2026-02-21 22:15:23.369268', '2026-02-21 22:35:43.768177', 'monitor:operlog:query', NULL, '查询操作日志详情', false, 35);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (454, '2026-02-21 22:15:23.370293', '2026-02-21 22:35:43.768202', 'ems:alarm-config:remove', NULL, '批量删除数据', false, 36);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (455, '2026-02-21 22:15:23.371307', '2026-02-21 22:35:43.768231', 'ems:alarm-limit-type:remove', NULL, '批量删除数据', false, 37);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (456, '2026-02-21 22:15:23.372246', '2026-02-21 22:35:43.768259', 'ems:alarm-record:handle', NULL, '处理报警记录', false, 38);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (457, '2026-02-21 22:15:23.374271', '2026-02-21 22:35:43.768284', 'ems:benchmark:remove', NULL, '批量删除数据', false, 39);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (458, '2026-02-21 22:15:23.375307', '2026-02-21 22:35:43.768309', 'ems:cost-policy-binding:remove', NULL, '批量删除数据', false, 40);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (459, '2026-02-21 22:15:23.376142', '2026-02-21 22:35:43.768565', 'ems:energy-cost-record:edit', NULL, '更新成本记录', false, 41);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (460, '2026-02-21 22:15:23.376895', '2026-02-21 22:35:43.768612', 'ems:energy-cost-record:remove', NULL, '批量删除数据', false, 41);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (461, '2026-02-21 22:15:23.377692', '2026-02-21 22:35:43.768659', 'ems:energy-saving-project:remove', NULL, '批量删除数据', false, 42);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (462, '2026-02-21 22:15:23.378488', '2026-02-21 22:35:43.768693', 'ems:energy-saving-project:edit', NULL, '更新项目状态', false, 42);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (463, '2026-02-21 22:15:23.379254', '2026-02-21 22:35:43.768722', 'ems:energy-type:remove', NULL, '批量删除数据', false, 43);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (464, '2026-02-21 22:15:23.380222', '2026-02-21 22:35:43.768749', 'ems:energy-type:edit', NULL, '修改能源类型状态', false, 43);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (465, '2026-02-21 22:15:23.381298', '2026-02-21 22:35:43.768783', 'ems:energy-unit:remove', NULL, '批量删除数据', false, 44);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (466, '2026-02-21 22:15:23.382566', '2026-02-21 22:35:43.768809', 'ems:energy-unit:edit', NULL, '修改用能单元状态', false, 44);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (467, '2026-02-21 22:15:23.38346', '2026-02-21 22:35:43.768836', 'ems:knowledge:edit', NULL, '更新文章状态', false, 45);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (468, '2026-02-21 22:15:23.384144', '2026-02-21 22:35:43.768861', 'ems:knowledge:remove', NULL, '删除文章', false, 45);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (469, '2026-02-21 22:15:23.384862', '2026-02-21 22:35:43.768889', 'ems:meter:edit', NULL, '更新计量器具', false, 46);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (470, '2026-02-21 22:15:23.385551', '2026-02-21 22:35:43.768916', 'ems:meter:remove', NULL, '批量删除数据', false, 46);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (471, '2026-02-21 22:15:23.386241', '2026-02-21 22:35:43.76894', 'ems:meter-point:remove', NULL, '批量删除数据', false, 47);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (473, '2026-02-21 22:15:23.387591', '2026-02-21 22:35:43.768989', 'ems:policy:edit', NULL, '更新政策状态', false, 48);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (474, '2026-02-21 22:15:23.388266', '2026-02-21 22:35:43.769012', 'ems:price-policy:edit', NULL, '修改电价策略状态', false, 49);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (475, '2026-02-21 22:15:23.389073', '2026-02-21 22:35:43.769035', 'ems:product:edit', NULL, '修改状态', false, 50);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (476, '2026-02-21 22:15:23.390094', '2026-02-21 22:35:43.769059', 'ems:production-record:edit', NULL, '更新产量记录', false, 51);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (477, '2026-02-21 22:15:23.391351', '2026-02-21 22:35:43.769089', 'ems:production-record:remove', NULL, '删除产量记录', false, 51);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (478, '2026-02-21 22:15:23.392227', '2026-02-21 22:35:43.769114', 'ems:time-period-price:edit', NULL, '修改状态', false, 52);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (499, '2026-02-23 18:50:12.639276', '2026-02-23 18:50:12.639276', 'ems:benchmark:query', NULL, '按ID查询', false, 39);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (500, '2026-02-23 18:50:12.653689', '2026-02-23 18:50:12.653689', 'ems:policy:query', NULL, '按ID查询', false, 48);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (501, '2026-02-23 18:50:12.655148', '2026-02-23 18:50:12.655148', 'ems:energy-unit:query', NULL, '按ID查询', false, 44);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (406, '2026-02-21 22:15:23.285417', '2026-02-23 18:37:11.979856', 'system:config:query', NULL, '查询参数详情', false, 25);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (427, '2026-02-21 22:15:23.337523', '2026-02-22 15:02:17.340891', 'system:permission:remove', NULL, '批量删除权限', true, 31);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (428, '2026-02-21 22:15:23.338702', '2026-02-22 15:02:17.340923', 'system:permission:sync', NULL, '全量同步权限', true, 31);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (437, '2026-02-21 22:15:23.347859', '2026-02-22 15:02:17.340966', 'system:role:remove', NULL, '批量删除角色', true, 32);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (481, '2026-02-22 20:36:21.220555', '2026-02-22 20:36:21.220555', 'system:user:assignRole', NULL, '分配用户角色', false, 33);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (404, '2026-02-21 22:15:23.281716', '2026-02-22 20:36:21.26925', 'system:config:remove', NULL, '批量删除参数', false, 25);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (440, '2026-02-21 22:15:23.35184', '2026-02-22 20:36:21.269288', 'system:user:edit', NULL, '修改用户', false, 33);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (415, '2026-02-21 22:15:23.315351', '2026-02-22 20:36:21.269315', 'system:menu:remove', NULL, '批量删除菜单', false, 28);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (472, '2026-02-21 22:15:23.386921', '2026-02-23 18:37:11.979902', 'ems:meter-point:edit', NULL, '修改采集点位状态', false, 47);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (502, '2026-02-23 18:50:12.656234', '2026-02-23 18:50:12.656234', 'ems:price-policy:query', NULL, '按ID查询', false, 49);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (503, '2026-02-23 18:50:12.657319', '2026-02-23 18:50:12.657319', 'ems:energy-type:query', NULL, '按ID查询', false, 43);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (504, '2026-02-23 18:50:12.658938', '2026-02-23 18:50:12.658938', 'ems:meter:query', NULL, '按ID查询', false, 46);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (505, '2026-02-23 18:50:12.661219', '2026-02-23 18:50:12.661219', 'ems:meter-point:query', NULL, '按ID查询', false, 47);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (506, '2026-02-23 18:50:12.662612', '2026-02-23 18:50:12.662612', 'ems:alarm-config:query', NULL, '按ID查询', false, 36);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (424, '2026-02-21 22:15:23.333072', '2026-02-23 19:20:50.626429', 'system:notice:list', NULL, '查询所有数据', false, 30);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (409, '2026-02-21 22:15:23.291841', '2026-02-26 08:30:01.876906', 'system:dept:list', NULL, '查询部门成员', false, 26);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (479, '2026-02-21 22:15:23.393109', '2026-02-21 22:35:43.769164', 'ems:time-period-price:remove', NULL, '删除分时电价配置', false, 52);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (480, '2026-02-21 22:15:23.394038', '2026-02-21 22:35:43.769585', 'system:dict:remove', NULL, '批量删除', false, 53);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (482, '2026-02-23 17:12:31.478116', '2026-02-23 17:12:31.478116', 'ems:production-record:list', NULL, '分页查询', false, 51);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (483, '2026-02-23 17:12:31.488312', '2026-02-23 17:12:31.488312', 'ems:product:list', NULL, '分页查询产品', false, 50);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (484, '2026-02-23 17:12:31.489892', '2026-02-23 17:12:31.489892', 'ems:benchmark:list', NULL, '分页查询', false, 39);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (485, '2026-02-23 17:12:31.49065', '2026-02-23 17:12:31.49065', 'ems:cost-policy-binding:list', NULL, '分页查询', false, 40);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (486, '2026-02-23 17:12:31.491335', '2026-02-23 17:12:31.491335', 'ems:alarm-record:list', NULL, '分页查询报警记录', false, 38);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (487, '2026-02-23 17:12:31.493039', '2026-02-23 17:12:31.493039', 'ems:alarm-limit-type:list', NULL, '分页查询', false, 37);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (488, '2026-02-23 17:12:31.493918', '2026-02-23 17:12:31.493918', 'ems:policy:list', NULL, '分页查询政策', false, 48);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (489, '2026-02-23 17:12:31.494878', '2026-02-23 17:12:31.494878', 'ems:energy-unit:list', NULL, '获取完整树形结构', false, 44);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (490, '2026-02-23 17:12:31.495772', '2026-02-23 17:12:31.495772', 'ems:price-policy:list', NULL, '分页查询', false, 49);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (491, '2026-02-23 17:12:31.496583', '2026-02-23 17:12:31.496583', 'ems:knowledge:list', NULL, '分页查询', false, 45);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (492, '2026-02-23 17:12:31.497851', '2026-02-23 17:12:31.497851', 'ems:energy-cost-record:list', NULL, '分页查询', false, 41);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (493, '2026-02-23 17:12:31.498999', '2026-02-23 17:12:31.498999', 'ems:energy-type:list', NULL, '分页查询', false, 43);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (494, '2026-02-23 17:12:31.500127', '2026-02-23 17:12:31.500127', 'ems:energy-saving:list', NULL, '分页查询节能项目', false, 55);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (495, '2026-02-23 17:12:31.501042', '2026-02-23 17:12:31.501042', 'ems:meter:list', NULL, '分页查询', false, 46);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (496, '2026-02-23 17:12:31.501907', '2026-02-23 17:12:31.501907', 'ems:meter-point:list', NULL, '分页查询', false, 47);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (497, '2026-02-23 17:12:31.502666', '2026-02-23 17:12:31.502666', 'ems:alarm-config:list', NULL, '根据采集点位查询配置', false, 36);
INSERT INTO public.sys_permission (id, created_at, updated_at, code, description, name, super_permission, module_id) VALUES (407, '2026-02-21 22:15:23.287757', '2026-02-26 08:30:01.871935', 'system:dept:edit', NULL, '批量移除部门成员', false, 26);


--
-- Data for Name: sys_post; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (1, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'CEO', '董事长', '公司最高负责人', 1, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (2, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'GM', '总经理', '公司日常运营负责人', 2, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (3, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'DGM', '副总经理', '协助总经理工作', 3, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (4, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'HR_M', '人事经理', '人力资源部负责人', 4, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (5, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'HR', '人事专员', '负责招聘、考勤等', 5, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (6, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'FIN_M', '财务经理', '财务部负责人', 6, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (7, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'ACC', '会计', '负责账务处理', 7, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (8, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'CASH', '出纳', '负责现金收支', 8, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (9, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'TECH_D', '技术总监', '负责公司技术战略', 9, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (10, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'DEV_M', '研发经理', '研发团队负责人', 10, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (11, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'DEV', '研发工程师', '负责系统开发', 11, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (12, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'TEST_M', '测试经理', '测试团队负责人', 12, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (13, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'TEST', '测试工程师', '负责系统测试', 13, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (14, '2026-02-10 03:52:43.217879', '2026-02-10 03:52:43.217879', 'OPS_M', '运维经理', '运维团队负责人', 14, 0);
INSERT INTO public.sys_post (id, created_at, updated_at, code, name, remark, sort_order, status) VALUES (15, '2026-02-10 03:52:43.217879', '2026-02-19 09:53:20.9041', 'OPS', '运维工程师', '负责系统运维', 22, 1);


--
-- Data for Name: sys_role; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_role (id, created_at, updated_at, code, member_count, name, permission_count, remark, data_scope, status) VALUES (1, '2026-02-22 07:28:30.743421', '2026-02-22 14:43:07.989641', 'role:sysem:manager', 0, '系统管理员', 0, NULL, 'SELF', 0);


--
-- Data for Name: sys_role_dept; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: sys_role_permission; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 431);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 432);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 435);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 433);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 434);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 450);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 452);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 427);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 403);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 429);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 426);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 422);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 421);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 458);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 423);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 411);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 453);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 462);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 415);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 461);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 480);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 475);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 404);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 464);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 471);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 436);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 449);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 443);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 467);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 414);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 418);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 410);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 437);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 469);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 445);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 465);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 428);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 470);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 456);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 416);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 463);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 419);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 402);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 472);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 444);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 409);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 448);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 446);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 430);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 460);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 457);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 454);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 474);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 417);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 424);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 440);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 425);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 466);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 405);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 408);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 459);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 473);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 420);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 439);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 401);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 441);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 468);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 455);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 438);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 447);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 442);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 413);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 412);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 406);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 407);
INSERT INTO public.sys_role_permission (role_id, permission_id) VALUES (1, 451);


--
-- Data for Name: sys_user; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_user (id, created_at, updated_at, email, nickname, password, phone, status, username, account_expire_at, credentials_expire_at, fail_login_count, last_login_at, avatar, dept_id, employee_no, gender, remark, real_name, super_admin) VALUES (1, '2025-12-20 09:19:46.014369', '2026-02-27 06:49:08.979172', 'snowmandxp@qq.com', '系统管理员', '$2a$10$mYkIwrDLxj04AjOZzv2Nv.TWbT/roKNJ4pHOadd.sUiS3cwq6eNjC', '18922482046', 0, 'admin', NULL, NULL, 0, '2026-02-27 06:49:08.961033', 'https://api.dicebear.com/7.x/notionists/svg?seed=1&backgroundColor=b6e3f4,c0aede,d1d4f9,ffd5dc,ffdfbf', NULL, NULL, 0, NULL, '邓雪平', true);
INSERT INTO public.sys_user (id, created_at, updated_at, email, nickname, password, phone, status, username, account_expire_at, credentials_expire_at, fail_login_count, last_login_at, avatar, dept_id, employee_no, gender, remark, real_name, super_admin) VALUES (99, '2026-02-17 21:42:48.195474', '2026-02-19 20:39:15.727913', 'wum@terra.com', NULL, '$2a$10$A4EvMQGs2OGbvFd4a/p2guCO4YWRxEgIXqs.vAC8y4P6CfjytwChK', '13155667788', 0, 'wu.meng', NULL, NULL, 0, NULL, NULL, 202, NULL, 1, '软件测试员', '吴梦', NULL);
INSERT INTO public.sys_user (id, created_at, updated_at, email, nickname, password, phone, status, username, account_expire_at, credentials_expire_at, fail_login_count, last_login_at, avatar, dept_id, employee_no, gender, remark, real_name, super_admin) VALUES (96, '2026-02-17 21:42:47.903052', '2026-02-19 20:39:23.741414', 'zhaox@terra.com', NULL, '$2a$10$LopqyBNVfFEyVqFLI2Vs9.y19Vkzkqz/H9BcGOar1y7YPWL07Rjmi', '15022334455', 0, 'zhao.xu', NULL, NULL, 0, NULL, NULL, 205, NULL, 0, '大客户总监', '赵旭', NULL);
INSERT INTO public.sys_user (id, created_at, updated_at, email, nickname, password, phone, status, username, account_expire_at, credentials_expire_at, fail_login_count, last_login_at, avatar, dept_id, employee_no, gender, remark, real_name, super_admin) VALUES (97, '2026-02-17 21:42:48.002475', '2026-02-23 19:34:02.903015', 'liuq@terra.com', NULL, '$2a$10$ZdHPoSpMYd57.XD42Jemo.02g4OzAK9th.2tzO6HZ1rbGnJdOnxju', '15833445566', 0, 'liu.qian', NULL, NULL, 0, NULL, NULL, 103, NULL, 1, '办公室主任', '刘倩', true);
INSERT INTO public.sys_user (id, created_at, updated_at, email, nickname, password, phone, status, username, account_expire_at, credentials_expire_at, fail_login_count, last_login_at, avatar, dept_id, employee_no, gender, remark, real_name, super_admin) VALUES (98, '2026-02-17 21:42:48.102911', '2026-02-25 21:52:08.542935', 'zhouk@terra.com', NULL, '$2a$10$d6PYQD/nyzWLs2x1/BpGLeeEL44H4uc2/5stblFxlcRGYF6MwO8jG', '18644556677', 0, 'zhou.kai', NULL, NULL, 0, NULL, NULL, NULL, NULL, 0, '前端小组长', '周凯', NULL);
INSERT INTO public.sys_user (id, created_at, updated_at, email, nickname, password, phone, status, username, account_expire_at, credentials_expire_at, fail_login_count, last_login_at, avatar, dept_id, employee_no, gender, remark, real_name, super_admin) VALUES (95, '2026-02-17 21:42:47.808667', '2026-02-25 21:52:19.688435', 'zhangl@terra.com', NULL, '$2a$10$IiGDOaJPVsg2MBZO5g5RW.FWCTu4Wyg3ETSTmyywPYqIPfpy6stA.', '13588990011', 0, 'zhang.lin', NULL, NULL, 0, NULL, NULL, NULL, NULL, 1, '招聘专员', '张琳', NULL);
INSERT INTO public.sys_user (id, created_at, updated_at, email, nickname, password, phone, status, username, account_expire_at, credentials_expire_at, fail_login_count, last_login_at, avatar, dept_id, employee_no, gender, remark, real_name, super_admin) VALUES (93, '2026-02-17 21:42:47.597778', '2026-02-25 21:52:54.918014', 'lih@terra.com', NULL, '$2a$10$vLSLeo9lPuJRJMg3XkTF3eA3Gv40Xxd4Proaz7ta9/avuq7YBkpoS', '13800138001', 1, 'li.hang', NULL, NULL, 0, '2026-02-17 21:59:02.431245', NULL, NULL, NULL, 0, '高级后端开发', '李航', NULL);


--
-- Data for Name: sys_user_notice; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_user_notice (id, created_at, updated_at, notice_id, read_time, user_id) VALUES (1, '2026-02-11 17:34:29.831007', '2026-02-11 17:34:29.831007', 7, '2026-02-11 17:34:29.82863', 1);
INSERT INTO public.sys_user_notice (id, created_at, updated_at, notice_id, read_time, user_id) VALUES (2, '2026-02-19 20:22:23.246771', '2026-02-19 20:22:23.246771', 8, '2026-02-19 20:22:23.243125', 1);


--
-- Data for Name: sys_user_permission; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: sys_user_post; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_user_post (user_id, post_id) VALUES (1, 1);
INSERT INTO public.sys_user_post (user_id, post_id) VALUES (93, 13);
INSERT INTO public.sys_user_post (user_id, post_id) VALUES (93, 15);


--
-- Data for Name: sys_user_role; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.sys_user_role (user_id, role_id) VALUES (99, 1);
INSERT INTO public.sys_user_role (user_id, role_id) VALUES (95, 1);
INSERT INTO public.sys_user_role (user_id, role_id) VALUES (93, 1);


--
-- Name: ems_alarm_config_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_alarm_config_id_seq', 30, true);


--
-- Name: ems_alarm_limit_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_alarm_limit_type_id_seq', 25, true);


--
-- Name: ems_alarm_record_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_alarm_record_id_seq', 7, true);


--
-- Name: ems_benchmark_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_benchmark_id_seq', 12, true);


--
-- Name: ems_cost_policy_binding_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_cost_policy_binding_id_seq', 5, true);


--
-- Name: ems_energy_cost_record_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_energy_cost_record_id_seq', 2, true);


--
-- Name: ems_energy_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_energy_data_id_seq', 538, true);


--
-- Name: ems_energy_saving_project_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_energy_saving_project_id_seq', 8, true);


--
-- Name: ems_energy_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_energy_type_id_seq', 28, true);


--
-- Name: ems_energy_unit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_energy_unit_id_seq', 73, true);


--
-- Name: ems_knowledge_article_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_knowledge_article_id_seq', 20, true);


--
-- Name: ems_meter_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_meter_id_seq', 21, true);


--
-- Name: ems_meter_point_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_meter_point_id_seq', 30, true);


--
-- Name: ems_peak_valley_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_peak_valley_data_id_seq', 64, true);


--
-- Name: ems_policy_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_policy_id_seq', 7, true);


--
-- Name: ems_price_policy_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_price_policy_id_seq', 6, true);


--
-- Name: ems_price_policy_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_price_policy_item_id_seq', 36, true);


--
-- Name: ems_product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_product_id_seq', 20, true);


--
-- Name: ems_production_record_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_production_record_id_seq', 24, true);


--
-- Name: ems_time_period_price_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ems_time_period_price_id_seq', 1, false);


--
-- Name: sys_config_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_config_id_seq', 1, false);


--
-- Name: sys_dept_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_dept_id_seq', 205, true);


--
-- Name: sys_dict_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_dict_data_id_seq', 27940, true);


--
-- Name: sys_dict_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_dict_type_id_seq', 18, true);


--
-- Name: sys_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_log_id_seq', 1, false);


--
-- Name: sys_login_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_login_log_id_seq', 103, true);


--
-- Name: sys_menu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_menu_id_seq', 1, false);


--
-- Name: sys_module_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_module_id_seq', 55, true);


--
-- Name: sys_notice_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_notice_id_seq', 8, true);


--
-- Name: sys_operation_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_operation_log_id_seq', 135, true);


--
-- Name: sys_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_permission_id_seq', 506, true);


--
-- Name: sys_post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_post_id_seq', 21, true);


--
-- Name: sys_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_role_id_seq', 1, true);


--
-- Name: sys_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_user_id_seq', 99, true);


--
-- Name: sys_user_notice_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.sys_user_notice_id_seq', 2, true);


--
-- Data for Name: 32793..32839; Type: BLOBS; Schema: -; Owner: -
--

BEGIN;

SELECT pg_catalog.lo_open('32793', 131072);
SELECT pg_catalog.lowrite(0, '\xe585ace58fb8e585a8e4bd93e59198e5b7a5efbc9a0a0ae580bce6ada4e696b0e698a5e4bdb3e88a82e69da5e4b8b4e4b98be99985efbc8ce6a0b9e68daee59bbde5aeb6e6b395e5ae9ae88a82e58187e697a5e8a784e5ae9aefbc8ce5b9b6e7bb93e59088e585ace58fb8e5ae9ee99985e68385e586b5efbc8ce78eb0e5b08632303235e5b9b4e698a5e88a82e694bee58187e5ae89e68e92e9809ae79fa5e5a682e4b88befbc9a0a0ae4b880e38081e694bee58187e697b6e997b40a32303235e5b9b431e69c883238e697a5efbc88e5869ce58e86e8858ae69c88e5bbbfe4b99defbc8ce6989fe69c9fe4ba8cefbc89e887b332e69c8833e697a5efbc88e5869ce58e86e6ada3e69c88e5889de585adefbc8ce6989fe69c9fe4b880efbc89e694bee58187e8b083e4bc91efbc8ce585b137e5a4a9e380820a32303235e5b9b432e69c8834e697a5efbc88e5869ce58e86e6ada3e69c88e5889de4b883efbc8ce6989fe69c9fe4ba8cefbc89e6ada3e5bc8fe4b88ae78fade380820a0ae4ba8ce38081e8b083e78fade5ae89e68e920a32303235e5b9b431e69c883236e697a5efbc88e6989fe69c9fe697a5efbc89e3808132e69c8838e697a5efbc88e6989fe69c9fe585adefbc89e6ada3e5b8b8e4b88ae78fade380820a0ae4b889e38081e79bb8e585b3e4ba8be9a1b90a0ae5b7a5e4bd9ce5ae89e68e92efbc9ae8afb7e59084e983a8e997a8e59198e5b7a5e59ca8e694bee58187e5898de5a6a5e59684e5ae8ce68890e69cace8818ce5b7a5e4bd9cefbc8ce6a380e69fa5e58a9ee585ace8aebee5a487efbc8ce585b3e997ade794b5e6ba90e38081e997a8e7aa97efbc8ce5819ae5a5bde998b2e781abe998b2e79b97e7ad89e5ae89e585a8e6a380e69fa5e5b7a5e4bd9ce380820a0ae580bce78fade5ae89e68e92efbc9ae5a682e69c89e99c80e8a681efbc8ce79bb8e585b3e983a8e997a8e99c80e68f90e5898de5ae89e68e92e5a5bde58187e69c9fe580bce78fade4babae59198efbc8ce5b9b6e5b086e5908de58d95e68aa5e887b3e8a18ce694bfe983a82fe7bbbce59088e7aea1e79086e983a8e5a487e6a188e38082e580bce78fade4babae59198e9a1bbe59d9ae5ae88e5b297e4bd8defbc8ce4bf9de68c81e9809ae8aeafe79585e9809ae380820a0ae58187e69c9fe5ae89e585a8efbc9ae58187e69c9fe69c9fe997b4efbc8ce8afb7e5a4a7e5aeb6e6b3a8e6848fe4baa4e9809ae38081e9a5aee9a39fe58f8ae4babae8baabe8b4a2e4baa7e5ae89e585a8efbc8ce5819ae5a5bde4b8aae4babae581a5e5bab7e998b2e68aa4efbc8ce5baa6e8bf87e4b880e4b8aae5b9b3e5ae89e38081e7a5a5e5928ce38081e68489e5bfabe79a84e698a5e88a82e380820a0ae88a82e5908ee8bf94e5b297efbc9ae8afb7e585a8e4bd93e59198e5b7a5e59088e79086e5ae89e68e92e8bf94e7a88be697b6e997b4efbc8ce7a1aee4bf9de88a82e5908ee58786e697b6e8bf94e5b297e38082e5a682e98187e789b9e6ae8ae68385e586b5e99c80e68f90e5898de8afb7e58187efbc8ce8afb7e68c89e585ace58fb8e8a784e5ae9ae58a9ee79086e8afb7e58187e6898be7bbade380820a0ae6849fe8b0a2e5a4a7e5aeb6e4b880e5b9b4e69da5e79a84e8be9be58ba4e4bb98e587baefbc81e9a284e7a59de585a8e4bd93e59198e5b7a5e58f8ae5aeb6e4babaefbc9a0ae696b0e698a5e5bfabe4b990efbc8ce9be99e9a9ace7b2bee7a59eefbc8ce99896e5aeb6e5b9b8e7a68fefbc8ce4b887e4ba8be5a682e6848fefbc810a0ae789b9e6ada4e9809ae79fa5e380820a0a5be585ace58fb8e5908de7a7b05d0a5be8a18ce694bfe983a82fe4babae58a9be8b584e6ba90e983a82fe58a9ee585ace5aea45d0a32303235e5b9b431e69c885858e697a50a');
SELECT pg_catalog.lo_close(0);

SELECT pg_catalog.lo_open('32794', 131072);
SELECT pg_catalog.lowrite(0, '\xe585ace58fb8e585a8e4bd93e59198e5b7a5efbc9a0a0ae580bce6ada4e696b0e698a5e4bdb3e88a82e69da5e4b8b4e4b98be99985efbc8ce6a0b9e68daee59bbde5aeb6e6b395e5ae9ae88a82e58187e697a5e8a784e5ae9aefbc8ce5b9b6e7bb93e59088e585ace58fb8e5ae9ee99985e68385e586b5efbc8ce78eb0e5b08632303235e5b9b4e698a5e88a82e694bee58187e5ae89e68e92e9809ae79fa5e5a682e4b88befbc9a0a0ae4b880e38081e694bee58187e697b6e997b40a32303235e5b9b431e69c883238e697a5efbc88e5869ce58e86e8858ae69c88e5bbbfe4b99defbc8ce6989fe69c9fe4ba8cefbc89e887b332e69c8833e697a5efbc88e5869ce58e86e6ada3e69c88e5889de585adefbc8ce6989fe69c9fe4b880efbc89e694bee58187e8b083e4bc91efbc8ce585b137e5a4a9e380820a32303235e5b9b432e69c8834e697a5efbc88e5869ce58e86e6ada3e69c88e5889de4b883efbc8ce6989fe69c9fe4ba8cefbc89e6ada3e5bc8fe4b88ae78fade380820a0ae4ba8ce38081e8b083e78fade5ae89e68e920a32303235e5b9b431e69c883236e697a5efbc88e6989fe69c9fe697a5efbc89e3808132e69c8838e697a5efbc88e6989fe69c9fe585adefbc89e6ada3e5b8b8e4b88ae78fade380820a0ae4b889e38081e79bb8e585b3e4ba8be9a1b90a0ae5b7a5e4bd9ce5ae89e68e92efbc9ae8afb7e59084e983a8e997a8e59198e5b7a5e59ca8e694bee58187e5898de5a6a5e59684e5ae8ce68890e69cace8818ce5b7a5e4bd9cefbc8ce6a380e69fa5e58a9ee585ace8aebee5a487efbc8ce585b3e997ade794b5e6ba90e38081e997a8e7aa97efbc8ce5819ae5a5bde998b2e781abe998b2e79b97e7ad89e5ae89e585a8e6a380e69fa5e5b7a5e4bd9ce380820a0ae580bce78fade5ae89e68e92efbc9ae5a682e69c89e99c80e8a681efbc8ce79bb8e585b3e983a8e997a8e99c80e68f90e5898de5ae89e68e92e5a5bde58187e69c9fe580bce78fade4babae59198efbc8ce5b9b6e5b086e5908de58d95e68aa5e887b3e8a18ce694bfe983a82fe7bbbce59088e7aea1e79086e983a8e5a487e6a188e38082e580bce78fade4babae59198e9a1bbe59d9ae5ae88e5b297e4bd8defbc8ce4bf9de68c81e9809ae8aeafe79585e9809ae380820a0ae58187e69c9fe5ae89e585a8efbc9ae58187e69c9fe69c9fe997b4efbc8ce8afb7e5a4a7e5aeb6e6b3a8e6848fe4baa4e9809ae38081e9a5aee9a39fe58f8ae4babae8baabe8b4a2e4baa7e5ae89e585a8efbc8ce5819ae5a5bde4b8aae4babae581a5e5bab7e998b2e68aa4efbc8ce5baa6e8bf87e4b880e4b8aae5b9b3e5ae89e38081e7a5a5e5928ce38081e68489e5bfabe79a84e698a5e88a82e380820a0ae88a82e5908ee8bf94e5b297efbc9ae8afb7e585a8e4bd93e59198e5b7a5e59088e79086e5ae89e68e92e8bf94e7a88be697b6e997b4efbc8ce7a1aee4bf9de88a82e5908ee58786e697b6e8bf94e5b297e38082e5a682e98187e789b9e6ae8ae68385e586b5e99c80e68f90e5898de8afb7e58187efbc8ce8afb7e68c89e585ace58fb8e8a784e5ae9ae58a9ee79086e8afb7e58187e6898be7bbade380820a0ae6849fe8b0a2e5a4a7e5aeb6e4b880e5b9b4e69da5e79a84e8be9be58ba4e4bb98e587baefbc81e9a284e7a59de585a8e4bd93e59198e5b7a5e58f8ae5aeb6e4babaefbc9a0ae696b0e698a5e5bfabe4b990efbc8ce9be99e9a9ace7b2bee7a59eefbc8ce99896e5aeb6e5b9b8e7a68fefbc8ce4b887e4ba8be5a682e6848fefbc810a0ae789b9e6ada4e9809ae79fa5e380820a0a5be585ace58fb8e5908de7a7b05d0a5be8a18ce694bfe983a82fe4babae58a9be8b584e6ba90e983a82fe58a9ee585ace5aea45d0a32303235e5b9b431e69c885858e697a5');
SELECT pg_catalog.lo_close(0);

SELECT pg_catalog.lo_open('32795', 131072);
SELECT pg_catalog.lowrite(0, '\xe585ace58fb8e585a8e4bd93e59198e5b7a5efbc9a0a0ae580bce6ada4e696b0e698a5e4bdb3e88a82e69da5e4b8b4e4b98be99985efbc8ce6a0b9e68daee59bbde5aeb6e6b395e5ae9ae88a82e58187e697a5e8a784e5ae9aefbc8ce5b9b6e7bb93e59088e585ace58fb8e5ae9ee99985e68385e586b5efbc8ce78eb0e5b08632303235e5b9b4e698a5e88a82e694bee58187e5ae89e68e92e9809ae79fa5e5a682e4b88befbc9a0a0ae4b880e38081e694bee58187e697b6e997b40a32303235e5b9b431e69c883238e697a5efbc88e5869ce58e86e8858ae69c88e5bbbfe4b99defbc8ce6989fe69c9fe4ba8cefbc89e887b332e69c8833e697a5efbc88e5869ce58e86e6ada3e69c88e5889de585adefbc8ce6989fe69c9fe4b880efbc89e694bee58187e8b083e4bc91efbc8ce585b137e5a4a9e380820a32303235e5b9b432e69c8834e697a5efbc88e5869ce58e86e6ada3e69c88e5889de4b883efbc8ce6989fe69c9fe4ba8cefbc89e6ada3e5bc8fe4b88ae78fade380820a0ae4ba8ce38081e8b083e78fade5ae89e68e920a32303235e5b9b431e69c883236e697a5efbc88e6989fe69c9fe697a5efbc89e3808132e69c8838e697a5efbc88e6989fe69c9fe585adefbc89e6ada3e5b8b8e4b88ae78fade380820a0ae4b889e38081e79bb8e585b3e4ba8be9a1b90a0ae5b7a5e4bd9ce5ae89e68e92efbc9ae8afb7e59084e983a8e997a8e59198e5b7a5e59ca8e694bee58187e5898de5a6a5e59684e5ae8ce68890e69cace8818ce5b7a5e4bd9cefbc8ce6a380e69fa5e58a9ee585ace8aebee5a487efbc8ce585b3e997ade794b5e6ba90e38081e997a8e7aa97efbc8ce5819ae5a5bde998b2e781abe998b2e79b97e7ad89e5ae89e585a8e6a380e69fa5e5b7a5e4bd9ce380820a0ae580bce78fade5ae89e68e92efbc9ae5a682e69c89e99c80e8a681efbc8ce79bb8e585b3e983a8e997a8e99c80e68f90e5898de5ae89e68e92e5a5bde58187e69c9fe580bce78fade4babae59198efbc8ce5b9b6e5b086e5908de58d95e68aa5e887b3e8a18ce694bfe983a82fe7bbbce59088e7aea1e79086e983a8e5a487e6a188e38082e580bce78fade4babae59198e9a1bbe59d9ae5ae88e5b297e4bd8defbc8ce4bf9de68c81e9809ae8aeafe79585e9809ae380820a0ae58187e69c9fe5ae89e585a8efbc9ae58187e69c9fe69c9fe997b4efbc8ce8afb7e5a4a7e5aeb6e6b3a8e6848fe4baa4e9809ae38081e9a5aee9a39fe58f8ae4babae8baabe8b4a2e4baa7e5ae89e585a8efbc8ce5819ae5a5bde4b8aae4babae581a5e5bab7e998b2e68aa4efbc8ce5baa6e8bf87e4b880e4b8aae5b9b3e5ae89e38081e7a5a5e5928ce38081e68489e5bfabe79a84e698a5e88a82e380820a0ae88a82e5908ee8bf94e5b297efbc9ae8afb7e585a8e4bd93e59198e5b7a5e59088e79086e5ae89e68e92e8bf94e7a88be697b6e997b4efbc8ce7a1aee4bf9de88a82e5908ee58786e697b6e8bf94e5b297e38082e5a682e98187e789b9e6ae8ae68385e586b5e99c80e68f90e5898de8afb7e58187efbc8ce8afb7e68c89e585ace58fb8e8a784e5ae9ae58a9ee79086e8afb7e58187e6898be7bbade380820a0ae6849fe8b0a2e5a4a7e5aeb6e4b880e5b9b4e69da5e79a84e8be9be58ba4e4bb98e587baefbc81e9a284e7a59de585a8e4bd93e59198e5b7a5e58f8ae5aeb6e4babaefbc9a0ae696b0e698a5e5bfabe4b990efbc8ce9be99e9a9ace7b2bee7a59eefbc8ce99896e5aeb6e5b9b8e7a68fefbc8ce4b887e4ba8be5a682e6848fefbc810a0ae789b9e6ada4e9809ae79fa5e380820a0a5be585ace58fb8e5908de7a7b05d0a5be8a18ce694bfe983a82fe4babae58a9be8b584e6ba90e983a82fe58a9ee585ace5aea45d0a32303235e5b9b431e69c885858e697a50a0a');
SELECT pg_catalog.lo_close(0);

SELECT pg_catalog.lo_open('32796', 131072);
SELECT pg_catalog.lowrite(0, '\xe585ace58fb8e585a8e4bd93e59198e5b7a5efbc9a0a0ae580bce6ada4e696b0e698a5e4bdb3e88a82e69da5e4b8b4e4b98be99985efbc8ce6a0b9e68daee59bbde5aeb6e6b395e5ae9ae88a82e58187e697a5e8a784e5ae9aefbc8ce5b9b6e7bb93e59088e585ace58fb8e5ae9ee99985e68385e586b5efbc8ce78eb0e5b08632303235e5b9b4e698a5e88a82e694bee58187e5ae89e68e92e9809ae79fa5e5a682e4b88befbc9a0a0ae4b880e38081e694bee58187e697b6e997b40a32303235e5b9b431e69c883238e697a5efbc88e5869ce58e86e8858ae69c88e5bbbfe4b99defbc8ce6989fe69c9fe4ba8cefbc89e887b332e69c8833e697a5efbc88e5869ce58e86e6ada3e69c88e5889de585adefbc8ce6989fe69c9fe4b880efbc89e694bee58187e8b083e4bc91efbc8ce585b137e5a4a9e380820a32303235e5b9b432e69c8834e697a5efbc88e5869ce58e86e6ada3e69c88e5889de4b883efbc8ce6989fe69c9fe4ba8cefbc89e6ada3e5bc8fe4b88ae78fade380820a0ae4ba8ce38081e8b083e78fade5ae89e68e920a32303235e5b9b431e69c883236e697a5efbc88e6989fe69c9fe697a5efbc89e3808132e69c8838e697a5efbc88e6989fe69c9fe585adefbc89e6ada3e5b8b8e4b88ae78fade380820a0ae4b889e38081e79bb8e585b3e4ba8be9a1b90a0ae5b7a5e4bd9ce5ae89e68e92efbc9ae8afb7e59084e983a8e997a8e59198e5b7a5e59ca8e694bee58187e5898de5a6a5e59684e5ae8ce68890e69cace8818ce5b7a5e4bd9cefbc8ce6a380e69fa5e58a9ee585ace8aebee5a487efbc8ce585b3e997ade794b5e6ba90e38081e997a8e7aa97efbc8ce5819ae5a5bde998b2e781abe998b2e79b97e7ad89e5ae89e585a8e6a380e69fa5e5b7a5e4bd9ce380820a0ae580bce78fade5ae89e68e92efbc9ae5a682e69c89e99c80e8a681efbc8ce79bb8e585b3e983a8e997a8e99c80e68f90e5898de5ae89e68e92e5a5bde58187e69c9fe580bce78fade4babae59198efbc8ce5b9b6e5b086e5908de58d95e68aa5e887b3e8a18ce694bfe983a82fe7bbbce59088e7aea1e79086e983a8e5a487e6a188e38082e580bce78fade4babae59198e9a1bbe59d9ae5ae88e5b297e4bd8defbc8ce4bf9de68c81e9809ae8aeafe79585e9809ae380820a0ae58187e69c9fe5ae89e585a8efbc9ae58187e69c9fe69c9fe997b4efbc8ce8afb7e5a4a7e5aeb6e6b3a8e6848fe4baa4e9809ae38081e9a5aee9a39fe58f8ae4babae8baabe8b4a2e4baa7e5ae89e585a8efbc8ce5819ae5a5bde4b8aae4babae581a5e5bab7e998b2e68aa4efbc8ce5baa6e8bf87e4b880e4b8aae5b9b3e5ae89e38081e7a5a5e5928ce38081e68489e5bfabe79a84e698a5e88a82e380820a0ae88a82e5908ee8bf94e5b297efbc9ae8afb7e585a8e4bd93e59198e5b7a5e59088e79086e5ae89e68e92e8bf94e7a88be697b6e997b4efbc8ce7a1aee4bf9de88a82e5908ee58786e697b6e8bf94e5b297e38082e5a682e98187e789b9e6ae8ae68385e586b5e99c80e68f90e5898de8afb7e58187efbc8ce8afb7e68c89e585ace58fb8e8a784e5ae9ae58a9ee79086e8afb7e58187e6898be7bbade380820a0ae6849fe8b0a2e5a4a7e5aeb6e4b880e5b9b4e69da5e79a84e8be9be58ba4e4bb98e587baefbc81e9a284e7a59de585a8e4bd93e59198e5b7a5e58f8ae5aeb6e4babaefbc9a0ae696b0e698a5e5bfabe4b990efbc8ce9be99e9a9ace7b2bee7a59eefbc8ce99896e5aeb6e5b9b8e7a68fefbc8ce4b887e4ba8be5a682e6848fefbc810a0ae789b9e6ada4e9809ae79fa5e380820a0a5be585ace58fb8e5908de7a7b05d0a5be8a18ce694bfe983a82fe4babae58a9be8b584e6ba90e983a82fe58a9ee585ace5aea45d0a32303235e5b9b431e69c885858e697a50a0a');
SELECT pg_catalog.lo_close(0);

SELECT pg_catalog.lo_open('32797', 131072);
SELECT pg_catalog.lowrite(0, '\xe585ace58fb8e585a8e4bd93e59198e5b7a5efbc9a0a0ae580bce6ada4e696b0e698a5e4bdb3e88a82e69da5e4b8b4e4b98be99985efbc8ce6a0b9e68daee59bbde5aeb6e6b395e5ae9ae88a82e58187e697a5e8a784e5ae9aefbc8ce5b9b6e7bb93e59088e585ace58fb8e5ae9ee99985e68385e586b5efbc8ce78eb0e5b08632303235e5b9b4e698a5e88a82e694bee58187e5ae89e68e92e9809ae79fa5e5a682e4b88befbc9a0a0ae4b880e38081e694bee58187e697b6e997b40a32303235e5b9b431e69c883238e697a5efbc88e5869ce58e86e8858ae69c88e5bbbfe4b99defbc8ce6989fe69c9fe4ba8cefbc89e887b332e69c8833e697a5efbc88e5869ce58e86e6ada3e69c88e5889de585adefbc8ce6989fe69c9fe4b880efbc89e694bee58187e8b083e4bc91efbc8ce585b137e5a4a9e380820a32303235e5b9b432e69c8834e697a5efbc88e5869ce58e86e6ada3e69c88e5889de4b883efbc8ce6989fe69c9fe4ba8cefbc89e6ada3e5bc8fe4b88ae78fade380820a0ae4ba8ce38081e8b083e78fade5ae89e68e920a32303235e5b9b431e69c883236e697a5efbc88e6989fe69c9fe697a5efbc89e3808132e69c8838e697a5efbc88e6989fe69c9fe585adefbc89e6ada3e5b8b8e4b88ae78fade380820a0ae4b889e38081e79bb8e585b3e4ba8be9a1b90a0ae5b7a5e4bd9ce5ae89e68e92efbc9ae8afb7e59084e983a8e997a8e59198e5b7a5e59ca8e694bee58187e5898de5a6a5e59684e5ae8ce68890e69cace8818ce5b7a5e4bd9cefbc8ce6a380e69fa5e58a9ee585ace8aebee5a487efbc8ce585b3e997ade794b5e6ba90e38081e997a8e7aa97efbc8ce5819ae5a5bde998b2e781abe998b2e79b97e7ad89e5ae89e585a8e6a380e69fa5e5b7a5e4bd9ce380820a0ae580bce78fade5ae89e68e92efbc9ae5a682e69c89e99c80e8a681efbc8ce79bb8e585b3e983a8e997a8e99c80e68f90e5898de5ae89e68e92e5a5bde58187e69c9fe580bce78fade4babae59198efbc8ce5b9b6e5b086e5908de58d95e68aa5e887b3e8a18ce694bfe983a82fe7bbbce59088e7aea1e79086e983a8e5a487e6a188e38082e580bce78fade4babae59198e9a1bbe59d9ae5ae88e5b297e4bd8defbc8ce4bf9de68c81e9809ae8aeafe79585e9809ae380820a0ae58187e69c9fe5ae89e585a8efbc9ae58187e69c9fe69c9fe997b4efbc8ce8afb7e5a4a7e5aeb6e6b3a8e6848fe4baa4e9809ae38081e9a5aee9a39fe58f8ae4babae8baabe8b4a2e4baa7e5ae89e585a8efbc8ce5819ae5a5bde4b8aae4babae581a5e5bab7e998b2e68aa4efbc8ce5baa6e8bf87e4b880e4b8aae5b9b3e5ae89e38081e7a5a5e5928ce38081e68489e5bfabe79a84e698a5e88a82e380820a0ae88a82e5908ee8bf94e5b297efbc9ae8afb7e585a8e4bd93e59198e5b7a5e59088e79086e5ae89e68e92e8bf94e7a88be697b6e997b4efbc8ce7a1aee4bf9de88a82e5908ee58786e697b6e8bf94e5b297e38082e5a682e98187e789b9e6ae8ae68385e586b5e99c80e68f90e5898de8afb7e58187efbc8ce8afb7e68c89e585ace58fb8e8a784e5ae9ae58a9ee79086e8afb7e58187e6898be7bbade380820a0ae6849fe8b0a2e5a4a7e5aeb6e4b880e5b9b4e69da5e79a84e8be9be58ba4e4bb98e587baefbc81e9a284e7a59de585a8e4bd93e59198e5b7a5e58f8ae5aeb6e4babaefbc9a0ae696b0e698a5e5bfabe4b990efbc8ce9be99e9a9ace7b2bee7a59eefbc8ce99896e5aeb6e5b9b8e7a68fefbc8ce4b887e4ba8be5a682e6848fefbc810a0ae789b9e6ada4e9809ae79fa5e380820a0a5be585ace58fb8e5908de7a7b05d0a5be8a18ce694bfe983a82fe4babae58a9be8b584e6ba90e983a82fe58a9ee585ace5aea45d0a32303235e5b9b431e69c885858e697a5');
SELECT pg_catalog.lo_close(0);

SELECT pg_catalog.lo_open('32798', 131072);
SELECT pg_catalog.lowrite(0, '\xe585ace58fb8e585a8e4bd93e59198e5b7a5efbc9a0a0ae580bce6ada4e696b0e698a5e4bdb3e88a82e69da5e4b8b4e4b98be99985efbc8ce6a0b9e68daee59bbde5aeb6e6b395e5ae9ae88a82e58187e697a5e8a784e5ae9aefbc8ce5b9b6e7bb93e59088e585ace58fb8e5ae9ee99985e68385e586b5efbc8ce78eb0e5b08632303235e5b9b4e698a5e88a82e694bee58187e5ae89e68e92e9809ae79fa5e5a682e4b88befbc9a0a0ae4b880e38081e694bee58187e697b6e997b40a32303235e5b9b431e69c883238e697a5efbc88e5869ce58e86e8858ae69c88e5bbbfe4b99defbc8ce6989fe69c9fe4ba8cefbc89e887b332e69c8833e697a5efbc88e5869ce58e86e6ada3e69c88e5889de585adefbc8ce6989fe69c9fe4b880efbc89e694bee58187e8b083e4bc91efbc8ce585b137e5a4a9e380820a32303235e5b9b432e69c8834e697a5efbc88e5869ce58e86e6ada3e69c88e5889de4b883efbc8ce6989fe69c9fe4ba8cefbc89e6ada3e5bc8fe4b88ae78fade380820a0ae4ba8ce38081e8b083e78fade5ae89e68e920a32303235e5b9b431e69c883236e697a5efbc88e6989fe69c9fe697a5efbc89e3808132e69c8838e697a5efbc88e6989fe69c9fe585adefbc89e6ada3e5b8b8e4b88ae78fade380820a0ae4b889e38081e79bb8e585b3e4ba8be9a1b90a0ae5b7a5e4bd9ce5ae89e68e92efbc9ae8afb7e59084e983a8e997a8e59198e5b7a5e59ca8e694bee58187e5898de5a6a5e59684e5ae8ce68890e69cace8818ce5b7a5e4bd9cefbc8ce6a380e69fa5e58a9ee585ace8aebee5a487efbc8ce585b3e997ade794b5e6ba90e38081e997a8e7aa97efbc8ce5819ae5a5bde998b2e781abe998b2e79b97e7ad89e5ae89e585a8e6a380e69fa5e5b7a5e4bd9ce380820a0ae580bce78fade5ae89e68e92efbc9ae5a682e69c89e99c80e8a681efbc8ce79bb8e585b3e983a8e997a8e99c80e68f90e5898de5ae89e68e92e5a5bde58187e69c9fe580bce78fade4babae59198efbc8ce5b9b6e5b086e5908de58d95e68aa5e887b3e8a18ce694bfe983a82fe7bbbce59088e7aea1e79086e983a8e5a487e6a188e38082e580bce78fade4babae59198e9a1bbe59d9ae5ae88e5b297e4bd8defbc8ce4bf9de68c81e9809ae8aeafe79585e9809ae380820a0ae58187e69c9fe5ae89e585a8efbc9ae58187e69c9fe69c9fe997b4efbc8ce8afb7e5a4a7e5aeb6e6b3a8e6848fe4baa4e9809ae38081e9a5aee9a39fe58f8ae4babae8baabe8b4a2e4baa7e5ae89e585a8efbc8ce5819ae5a5bde4b8aae4babae581a5e5bab7e998b2e68aa4efbc8ce5baa6e8bf87e4b880e4b8aae5b9b3e5ae89e38081e7a5a5e5928ce38081e68489e5bfabe79a84e698a5e88a82e380820a0ae88a82e5908ee8bf94e5b297efbc9ae8afb7e585a8e4bd93e59198e5b7a5e59088e79086e5ae89e68e92e8bf94e7a88be697b6e997b4efbc8ce7a1aee4bf9de88a82e5908ee58786e697b6e8bf94e5b297e38082e5a682e98187e789b9e6ae8ae68385e586b5e99c80e68f90e5898de8afb7e58187efbc8ce8afb7e68c89e585ace58fb8e8a784e5ae9ae58a9ee79086e8afb7e58187e6898be7bbade380820a0ae6849fe8b0a2e5a4a7e5aeb6e4b880e5b9b4e69da5e79a84e8be9be58ba4e4bb98e587baefbc81e9a284e7a59de585a8e4bd93e59198e5b7a5e58f8ae5aeb6e4babaefbc9a0ae696b0e698a5e5bfabe4b990efbc8ce9be99e9a9ace7b2bee7a59eefbc8ce99896e5aeb6e5b9b8e7a68fefbc8ce4b887e4ba8be5a682e6848fefbc810a0ae789b9e6ada4e9809ae79fa5e380820a0a5be585ace58fb8e5908de7a7b05d0a5be8a18ce694bfe983a82fe4babae58a9be8b584e6ba90e983a82fe58a9ee585ace5aea45d0a32303235e5b9b431e69c885858e697a5');
SELECT pg_catalog.lo_close(0);

SELECT pg_catalog.lo_open('32799', 131072);
SELECT pg_catalog.lowrite(0, '\xe585ace58fb8e585a8e4bd93e59198e5b7a5efbc9a0a0ae580bce6ada4e696b0e698a5e4bdb3e88a82e69da5e4b8b4e4b98be99985efbc8ce6a0b9e68daee59bbde5aeb6e6b395e5ae9ae88a82e58187e697a5e8a784e5ae9aefbc8ce5b9b6e7bb93e59088e585ace58fb8e5ae9ee99985e68385e586b5efbc8ce78eb0e5b08632303235e5b9b4e698a5e88a82e694bee58187e5ae89e68e92e9809ae79fa5e5a682e4b88befbc9a0a0ae4b880e38081e694bee58187e697b6e997b40a32303235e5b9b431e69c883238e697a5efbc88e5869ce58e86e8858ae69c88e5bbbfe4b99defbc8ce6989fe69c9fe4ba8cefbc89e887b332e69c8833e697a5efbc88e5869ce58e86e6ada3e69c88e5889de585adefbc8ce6989fe69c9fe4b880efbc89e694bee58187e8b083e4bc91efbc8ce585b137e5a4a9e380820a32303235e5b9b432e69c8834e697a5efbc88e5869ce58e86e6ada3e69c88e5889de4b883efbc8ce6989fe69c9fe4ba8cefbc89e6ada3e5bc8fe4b88ae78fade380820a0ae4ba8ce38081e8b083e78fade5ae89e68e920a32303235e5b9b431e69c883236e697a5efbc88e6989fe69c9fe697a5efbc89e3808132e69c8838e697a5efbc88e6989fe69c9fe585adefbc89e6ada3e5b8b8e4b88ae78fade380820a0ae4b889e38081e79bb8e585b3e4ba8be9a1b90a0ae5b7a5e4bd9ce5ae89e68e92efbc9ae8afb7e59084e983a8e997a8e59198e5b7a5e59ca8e694bee58187e5898de5a6a5e59684e5ae8ce68890e69cace8818ce5b7a5e4bd9cefbc8ce6a380e69fa5e58a9ee585ace8aebee5a487efbc8ce585b3e997ade794b5e6ba90e38081e997a8e7aa97efbc8ce5819ae5a5bde998b2e781abe998b2e79b97e7ad89e5ae89e585a8e6a380e69fa5e5b7a5e4bd9ce380820a0ae580bce78fade5ae89e68e92efbc9ae5a682e69c89e99c80e8a681efbc8ce79bb8e585b3e983a8e997a8e99c80e68f90e5898de5ae89e68e92e5a5bde58187e69c9fe580bce78fade4babae59198efbc8ce5b9b6e5b086e5908de58d95e68aa5e887b3e8a18ce694bfe983a82fe7bbbce59088e7aea1e79086e983a8e5a487e6a188e38082e580bce78fade4babae59198e9a1bbe59d9ae5ae88e5b297e4bd8defbc8ce4bf9de68c81e9809ae8aeafe79585e9809ae380820a0ae58187e69c9fe5ae89e585a8efbc9ae58187e69c9fe69c9fe997b4efbc8ce8afb7e5a4a7e5aeb6e6b3a8e6848fe4baa4e9809ae38081e9a5aee9a39fe58f8ae4babae8baabe8b4a2e4baa7e5ae89e585a8efbc8ce5819ae5a5bde4b8aae4babae581a5e5bab7e998b2e68aa4efbc8ce5baa6e8bf87e4b880e4b8aae5b9b3e5ae89e38081e7a5a5e5928ce38081e68489e5bfabe79a84e698a5e88a82e380820a0ae88a82e5908ee8bf94e5b297efbc9ae8afb7e585a8e4bd93e59198e5b7a5e59088e79086e5ae89e68e92e8bf94e7a88be697b6e997b4efbc8ce7a1aee4bf9de88a82e5908ee58786e697b6e8bf94e5b297e38082e5a682e98187e789b9e6ae8ae68385e586b5e99c80e68f90e5898de8afb7e58187efbc8ce8afb7e68c89e585ace58fb8e8a784e5ae9ae58a9ee79086e8afb7e58187e6898be7bbade380820a0ae6849fe8b0a2e5a4a7e5aeb6e4b880e5b9b4e69da5e79a84e8be9be58ba4e4bb98e587baefbc81e9a284e7a59de585a8e4bd93e59198e5b7a5e58f8ae5aeb6e4babaefbc9a0ae696b0e698a5e5bfabe4b990efbc8ce9be99e9a9ace7b2bee7a59eefbc8ce99896e5aeb6e5b9b8e7a68fefbc8ce4b887e4ba8be5a682e6848fefbc810a0ae789b9e6ada4e9809ae79fa5e380820a0a5be585ace58fb8e5908de7a7b05d0a5be8a18ce694bfe983a82fe4babae58a9be8b584e6ba90e983a82fe58a9ee585ace5aea45d0a32303235e5b9b431e69c885858e697a5');
SELECT pg_catalog.lo_close(0);

SELECT pg_catalog.lo_open('32839', 131072);
SELECT pg_catalog.lowrite(0, '\xe585ace58fb8e585a8e4bd93e59198e5b7a5efbc9a0a0ae580bce6ada4e696b0e698a5e4bdb3e88a82e69da5e4b8b4e4b98be99985efbc8ce6a0b9e68daee59bbde5aeb6e6b395e5ae9ae88a82e58187e697a5e8a784e5ae9aefbc8ce5b9b6e7bb93e59088e585ace58fb8e5ae9ee99985e68385e586b5efbc8ce78eb0e5b08632303235e5b9b4e698a5e88a82e694bee58187e5ae89e68e92e9809ae79fa5e5a682e4b88befbc9a0a0ae4b880e38081e694bee58187e697b6e997b40a32303235e5b9b431e69c883238e697a5efbc88e5869ce58e86e8858ae69c88e5bbbfe4b99defbc8ce6989fe69c9fe4ba8cefbc89e887b332e69c8833e697a5efbc88e5869ce58e86e6ada3e69c88e5889de585adefbc8ce6989fe69c9fe4b880efbc89e694bee58187e8b083e4bc91efbc8ce585b137e5a4a9e380820a32303235e5b9b432e69c8834e697a5efbc88e5869ce58e86e6ada3e69c88e5889de4b883efbc8ce6989fe69c9fe4ba8cefbc89e6ada3e5bc8fe4b88ae78fade380820a0ae4ba8ce38081e8b083e78fade5ae89e68e920a32303235e5b9b431e69c883236e697a5efbc88e6989fe69c9fe697a5efbc89e3808132e69c8838e697a5efbc88e6989fe69c9fe585adefbc89e6ada3e5b8b8e4b88ae78fade380820a0ae4b889e38081e79bb8e585b3e4ba8be9a1b90a0ae5b7a5e4bd9ce5ae89e68e92efbc9ae8afb7e59084e983a8e997a8e59198e5b7a5e59ca8e694bee58187e5898de5a6a5e59684e5ae8ce68890e69cace8818ce5b7a5e4bd9cefbc8ce6a380e69fa5e58a9ee585ace8aebee5a487efbc8ce585b3e997ade794b5e6ba90e38081e997a8e7aa97efbc8ce5819ae5a5bde998b2e781abe998b2e79b97e7ad89e5ae89e585a8e6a380e69fa5e5b7a5e4bd9ce380820a0ae580bce78fade5ae89e68e92efbc9ae5a682e69c89e99c80e8a681efbc8ce79bb8e585b3e983a8e997a8e99c80e68f90e5898de5ae89e68e92e5a5bde58187e69c9fe580bce78fade4babae59198efbc8ce5b9b6e5b086e5908de58d95e68aa5e887b3e8a18ce694bfe983a82fe7bbbce59088e7aea1e79086e983a8e5a487e6a188e38082e580bce78fade4babae59198e9a1bbe59d9ae5ae88e5b297e4bd8defbc8ce4bf9de68c81e9809ae8aeafe79585e9809ae380820a0ae58187e69c9fe5ae89e585a8efbc9ae58187e69c9fe69c9fe997b4efbc8ce8afb7e5a4a7e5aeb6e6b3a8e6848fe4baa4e9809ae38081e9a5aee9a39fe58f8ae4babae8baabe8b4a2e4baa7e5ae89e585a8efbc8ce5819ae5a5bde4b8aae4babae581a5e5bab7e998b2e68aa4efbc8ce5baa6e8bf87e4b880e4b8aae5b9b3e5ae89e38081e7a5a5e5928ce38081e68489e5bfabe79a84e698a5e88a82e380820a0ae88a82e5908ee8bf94e5b297efbc9ae8afb7e585a8e4bd93e59198e5b7a5e59088e79086e5ae89e68e92e8bf94e7a88be697b6e997b4efbc8ce7a1aee4bf9de88a82e5908ee58786e697b6e8bf94e5b297e38082e5a682e98187e789b9e6ae8ae68385e586b5e99c80e68f90e5898de8afb7e58187efbc8ce8afb7e68c89e585ace58fb8e8a784e5ae9ae58a9ee79086e8afb7e58187e6898be7bbade380820a0ae6849fe8b0a2e5a4a7e5aeb6e4b880e5b9b4e69da5e79a84e8be9be58ba4e4bb98e587baefbc81e9a284e7a59de585a8e4bd93e59198e5b7a5e58f8ae5aeb6e4babaefbc9a0ae696b0e698a5e5bfabe4b990efbc8ce9be99e9a9ace7b2bee7a59eefbc8ce99896e5aeb6e5b9b8e7a68fefbc8ce4b887e4ba8be5a682e6848fefbc810a0ae789b9e6ada4e9809ae79fa5e380820a0a5be585ace58fb8e5908de7a7b05d0a5be8a18ce694bfe983a82fe4babae58a9be8b584e6ba90e983a82fe58a9ee585ace5aea45d0a32303235e5b9b431e69c885858e697a5');
SELECT pg_catalog.lo_close(0);

COMMIT;

--
-- Name: ems_alarm_config ems_alarm_config_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_alarm_config
    ADD CONSTRAINT ems_alarm_config_pkey PRIMARY KEY (id);


--
-- Name: ems_alarm_limit_type ems_alarm_limit_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_alarm_limit_type
    ADD CONSTRAINT ems_alarm_limit_type_pkey PRIMARY KEY (id);


--
-- Name: ems_alarm_record ems_alarm_record_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_alarm_record
    ADD CONSTRAINT ems_alarm_record_pkey PRIMARY KEY (id);


--
-- Name: ems_benchmark ems_benchmark_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_benchmark
    ADD CONSTRAINT ems_benchmark_pkey PRIMARY KEY (id);


--
-- Name: ems_cost_policy_binding ems_cost_policy_binding_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_cost_policy_binding
    ADD CONSTRAINT ems_cost_policy_binding_pkey PRIMARY KEY (id);


--
-- Name: ems_energy_cost_record ems_energy_cost_record_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_cost_record
    ADD CONSTRAINT ems_energy_cost_record_pkey PRIMARY KEY (id);


--
-- Name: ems_energy_data ems_energy_data_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_data
    ADD CONSTRAINT ems_energy_data_pkey PRIMARY KEY (id);


--
-- Name: ems_energy_saving_project ems_energy_saving_project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_saving_project
    ADD CONSTRAINT ems_energy_saving_project_pkey PRIMARY KEY (id);


--
-- Name: ems_energy_type ems_energy_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_type
    ADD CONSTRAINT ems_energy_type_pkey PRIMARY KEY (id);


--
-- Name: ems_energy_unit ems_energy_unit_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_unit
    ADD CONSTRAINT ems_energy_unit_pkey PRIMARY KEY (id);


--
-- Name: ems_energy_unit_point ems_energy_unit_point_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_unit_point
    ADD CONSTRAINT ems_energy_unit_point_pkey PRIMARY KEY (meter_point_id, energy_unit_id);


--
-- Name: ems_knowledge_article ems_knowledge_article_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_knowledge_article
    ADD CONSTRAINT ems_knowledge_article_pkey PRIMARY KEY (id);


--
-- Name: ems_meter ems_meter_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_meter
    ADD CONSTRAINT ems_meter_pkey PRIMARY KEY (id);


--
-- Name: ems_meter_point ems_meter_point_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_meter_point
    ADD CONSTRAINT ems_meter_point_pkey PRIMARY KEY (id);


--
-- Name: ems_peak_valley_data ems_peak_valley_data_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_peak_valley_data
    ADD CONSTRAINT ems_peak_valley_data_pkey PRIMARY KEY (id);


--
-- Name: ems_policy ems_policy_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_policy
    ADD CONSTRAINT ems_policy_pkey PRIMARY KEY (id);


--
-- Name: ems_price_policy_item ems_price_policy_item_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_price_policy_item
    ADD CONSTRAINT ems_price_policy_item_pkey PRIMARY KEY (id);


--
-- Name: ems_price_policy ems_price_policy_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_price_policy
    ADD CONSTRAINT ems_price_policy_pkey PRIMARY KEY (id);


--
-- Name: ems_product ems_product_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_product
    ADD CONSTRAINT ems_product_pkey PRIMARY KEY (id);


--
-- Name: ems_production_record ems_production_record_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_production_record
    ADD CONSTRAINT ems_production_record_pkey PRIMARY KEY (id);


--
-- Name: ems_time_period_price ems_time_period_price_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_time_period_price
    ADD CONSTRAINT ems_time_period_price_pkey PRIMARY KEY (id);


--
-- Name: sys_dict_type idx_dict_type; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_dict_type
    ADD CONSTRAINT idx_dict_type UNIQUE (type);


--
-- Name: sys_config sys_config_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_config
    ADD CONSTRAINT sys_config_pkey PRIMARY KEY (id);


--
-- Name: sys_dept sys_dept_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_dept
    ADD CONSTRAINT sys_dept_pkey PRIMARY KEY (id);


--
-- Name: sys_dict_data sys_dict_data_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_dict_data
    ADD CONSTRAINT sys_dict_data_pkey PRIMARY KEY (id);


--
-- Name: sys_dict_type sys_dict_type_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_dict_type
    ADD CONSTRAINT sys_dict_type_pkey PRIMARY KEY (id);


--
-- Name: sys_log sys_log_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_log
    ADD CONSTRAINT sys_log_pkey PRIMARY KEY (id);


--
-- Name: sys_login_log sys_login_log_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_login_log
    ADD CONSTRAINT sys_login_log_pkey PRIMARY KEY (id);


--
-- Name: sys_menu sys_menu_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_menu
    ADD CONSTRAINT sys_menu_pkey PRIMARY KEY (id);


--
-- Name: sys_module sys_module_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_module
    ADD CONSTRAINT sys_module_pkey PRIMARY KEY (id);


--
-- Name: sys_notice sys_notice_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_notice
    ADD CONSTRAINT sys_notice_pkey PRIMARY KEY (id);


--
-- Name: sys_operation_log sys_operation_log_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_operation_log
    ADD CONSTRAINT sys_operation_log_pkey PRIMARY KEY (id);


--
-- Name: sys_permission sys_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_permission
    ADD CONSTRAINT sys_permission_pkey PRIMARY KEY (id);


--
-- Name: sys_post sys_post_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_post
    ADD CONSTRAINT sys_post_pkey PRIMARY KEY (id);


--
-- Name: sys_role_permission sys_role_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_role_permission
    ADD CONSTRAINT sys_role_permission_pkey PRIMARY KEY (role_id, permission_id);


--
-- Name: sys_role sys_role_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_role
    ADD CONSTRAINT sys_role_pkey PRIMARY KEY (id);


--
-- Name: sys_user_notice sys_user_notice_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_notice
    ADD CONSTRAINT sys_user_notice_pkey PRIMARY KEY (id);


--
-- Name: sys_user_permission sys_user_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_permission
    ADD CONSTRAINT sys_user_permission_pkey PRIMARY KEY (user_id, permission_id);


--
-- Name: sys_user sys_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user
    ADD CONSTRAINT sys_user_pkey PRIMARY KEY (id);


--
-- Name: sys_user_post sys_user_post_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_post
    ADD CONSTRAINT sys_user_post_pkey PRIMARY KEY (user_id, post_id);


--
-- Name: sys_user_role sys_user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_role
    ADD CONSTRAINT sys_user_role_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: ems_energy_unit uk1dd9o4p4mktlib0tq3gpnl2gu; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_unit
    ADD CONSTRAINT uk1dd9o4p4mktlib0tq3gpnl2gu UNIQUE (code);


--
-- Name: sys_permission uk2vm98en2ouht0v15fvef2whp4; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_permission
    ADD CONSTRAINT uk2vm98en2ouht0v15fvef2whp4 UNIQUE (code);


--
-- Name: sys_user uk51bvuyvihefoh4kp5syh2jpi4; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user
    ADD CONSTRAINT uk51bvuyvihefoh4kp5syh2jpi4 UNIQUE (username);


--
-- Name: ems_benchmark uk541184xedrs6lp0yd95kiplis; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_benchmark
    ADD CONSTRAINT uk541184xedrs6lp0yd95kiplis UNIQUE (code);


--
-- Name: ems_meter uk5xsxp2n696rrf80766t933ly8; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_meter
    ADD CONSTRAINT uk5xsxp2n696rrf80766t933ly8 UNIQUE (code);


--
-- Name: sys_dict_data uk_dict_data_value_type; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_dict_data
    ADD CONSTRAINT uk_dict_data_value_type UNIQUE (value, type_code);


--
-- Name: ems_alarm_limit_type ukco6bbk2823t0js0qnrd41naxu; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_alarm_limit_type
    ADD CONSTRAINT ukco6bbk2823t0js0qnrd41naxu UNIQUE (limit_code);


--
-- Name: sys_user_notice uke8tesnqlq4mwewoyko7609fxb; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_notice
    ADD CONSTRAINT uke8tesnqlq4mwewoyko7609fxb UNIQUE (user_id, notice_id);


--
-- Name: sys_config ukellx5pgxddrdrfyvtfbgxccvl; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_config
    ADD CONSTRAINT ukellx5pgxddrdrfyvtfbgxccvl UNIQUE (config_key);


--
-- Name: ems_product ukj7ymku309maor470onudrjq3n; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_product
    ADD CONSTRAINT ukj7ymku309maor470onudrjq3n UNIQUE (code);


--
-- Name: ems_meter_point ukjb553gu8ityk06dbghpfbmroy; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_meter_point
    ADD CONSTRAINT ukjb553gu8ityk06dbghpfbmroy UNIQUE (code);


--
-- Name: ems_price_policy ukks5jbvi22h30gn1g1fysder64; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_price_policy
    ADD CONSTRAINT ukks5jbvi22h30gn1g1fysder64 UNIQUE (code);


--
-- Name: sys_module uknoavfxvjvs053hbvxfixe0gw2; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_module
    ADD CONSTRAINT uknoavfxvjvs053hbvxfixe0gw2 UNIQUE (code);


--
-- Name: sys_role ukplpigyqwsqfn7mn66npgf9ftp; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_role
    ADD CONSTRAINT ukplpigyqwsqfn7mn66npgf9ftp UNIQUE (code);


--
-- Name: sys_user ukpulp17fvich5aby4m0kc820h6; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user
    ADD CONSTRAINT ukpulp17fvich5aby4m0kc820h6 UNIQUE (phone);


--
-- Name: ems_energy_type ukqyk5p9pr4r421rh8un10ddyi6; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_type
    ADD CONSTRAINT ukqyk5p9pr4r421rh8un10ddyi6 UNIQUE (code);


--
-- Name: sys_post ukr5b7w4kya2gmxcc1asyns6odk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_post
    ADD CONSTRAINT ukr5b7w4kya2gmxcc1asyns6odk UNIQUE (code);


--
-- Name: idx_alarm_config_limit; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_alarm_config_limit ON public.ems_alarm_config USING btree (limit_type_id);


--
-- Name: idx_alarm_config_meter_point; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_alarm_config_meter_point ON public.ems_alarm_config USING btree (meter_point_id);


--
-- Name: idx_alarm_limit_type_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_alarm_limit_type_code ON public.ems_alarm_limit_type USING btree (limit_code);


--
-- Name: idx_alarm_record_config; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_alarm_record_config ON public.ems_alarm_record USING btree (config_id);


--
-- Name: idx_alarm_record_time; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_alarm_record_time ON public.ems_alarm_record USING btree (trigger_time);


--
-- Name: idx_benchmark_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_benchmark_code ON public.ems_benchmark USING btree (code);


--
-- Name: idx_benchmark_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_benchmark_type ON public.ems_benchmark USING btree (type);


--
-- Name: idx_cpb_energy_unit; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_cpb_energy_unit ON public.ems_cost_policy_binding USING btree (energy_unit_id);


--
-- Name: idx_cpb_price_policy; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_cpb_price_policy ON public.ems_cost_policy_binding USING btree (price_policy_id);


--
-- Name: idx_dict_data_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_dict_data_type ON public.sys_dict_data USING btree (type_code);


--
-- Name: idx_ecr_energy_unit; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_ecr_energy_unit ON public.ems_energy_cost_record USING btree (energy_unit_id);


--
-- Name: idx_ecr_period_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_ecr_period_type ON public.ems_energy_cost_record USING btree (period_type);


--
-- Name: idx_ecr_record_date; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_ecr_record_date ON public.ems_energy_cost_record USING btree (record_date);


--
-- Name: idx_energy_data_composite; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_energy_data_composite ON public.ems_energy_data USING btree (meter_point_id, data_time, time_type);


--
-- Name: idx_energy_data_point; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_energy_data_point ON public.ems_energy_data USING btree (meter_point_id);


--
-- Name: idx_energy_data_time; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_energy_data_time ON public.ems_energy_data USING btree (data_time, time_type);


--
-- Name: idx_energy_data_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_energy_data_type ON public.ems_energy_data USING btree (energy_type_id);


--
-- Name: idx_energy_type_category; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_energy_type_category ON public.ems_energy_type USING btree (category);


--
-- Name: idx_energy_type_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_energy_type_code ON public.ems_energy_type USING btree (code);


--
-- Name: idx_energy_unit_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_energy_unit_code ON public.ems_energy_unit USING btree (code);


--
-- Name: idx_energy_unit_parent; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_energy_unit_parent ON public.ems_energy_unit USING btree (parent_id);


--
-- Name: idx_energy_unit_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_energy_unit_type ON public.ems_energy_unit USING btree (unit_type);


--
-- Name: idx_knowledge_energy_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_knowledge_energy_type ON public.ems_knowledge_article USING btree (energy_type_id);


--
-- Name: idx_knowledge_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_knowledge_status ON public.ems_knowledge_article USING btree (status);


--
-- Name: idx_meter_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_meter_code ON public.ems_meter USING btree (code);


--
-- Name: idx_meter_energy_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_meter_energy_type ON public.ems_meter USING btree (energy_type_id);


--
-- Name: idx_meter_point_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_meter_point_code ON public.ems_meter_point USING btree (code);


--
-- Name: idx_meter_point_energy_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_meter_point_energy_type ON public.ems_meter_point USING btree (energy_type_id);


--
-- Name: idx_meter_point_meter; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_meter_point_meter ON public.ems_meter_point USING btree (meter_id);


--
-- Name: idx_meter_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_meter_type ON public.ems_meter USING btree (type);


--
-- Name: idx_permission_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_permission_code ON public.sys_permission USING btree (code);


--
-- Name: idx_permission_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_permission_id ON public.sys_permission USING btree (id);


--
-- Name: idx_policy_issuing_date; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_policy_issuing_date ON public.ems_policy USING btree (issuing_date);


--
-- Name: idx_policy_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_policy_type ON public.ems_policy USING btree (type);


--
-- Name: idx_price_policy_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_price_policy_code ON public.ems_price_policy USING btree (code);


--
-- Name: idx_price_policy_energy_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_price_policy_energy_type ON public.ems_price_policy USING btree (energy_type_id);


--
-- Name: idx_price_policy_item_policy; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_price_policy_item_policy ON public.ems_price_policy_item USING btree (policy_id);


--
-- Name: idx_product_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_code ON public.ems_product USING btree (code);


--
-- Name: idx_product_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_product_status ON public.ems_product USING btree (status);


--
-- Name: idx_production_date; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_production_date ON public.ems_production_record USING btree (record_date);


--
-- Name: idx_production_energy_unit; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_production_energy_unit ON public.ems_production_record USING btree (energy_unit_id);


--
-- Name: idx_production_product; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_production_product ON public.ems_production_record USING btree (product_name);


--
-- Name: idx_pvd_data_time; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_pvd_data_time ON public.ems_peak_valley_data USING btree (data_time, time_type);


--
-- Name: idx_pvd_energy_unit; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_pvd_energy_unit ON public.ems_peak_valley_data USING btree (energy_unit_id);


--
-- Name: idx_pvd_meter_point; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_pvd_meter_point ON public.ems_peak_valley_data USING btree (meter_point_id);


--
-- Name: idx_role_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_role_code ON public.sys_role USING btree (code);


--
-- Name: idx_role_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_role_id ON public.sys_role USING btree (id);


--
-- Name: idx_role_perm_permission_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_role_perm_permission_id ON public.sys_role_permission USING btree (permission_id);


--
-- Name: idx_role_perm_role_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_role_perm_role_id ON public.sys_role_permission USING btree (role_id);


--
-- Name: idx_saving_project_liable; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_saving_project_liable ON public.ems_energy_saving_project USING btree (liable_person);


--
-- Name: idx_saving_project_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_saving_project_status ON public.ems_energy_saving_project USING btree (status);


--
-- Name: idx_sys_dept_parent; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_dept_parent ON public.sys_dept USING btree (parent_id);


--
-- Name: idx_sys_dept_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_dept_status ON public.sys_dept USING btree (status);


--
-- Name: idx_sys_log_time; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_log_time ON public.sys_log USING btree (created_at);


--
-- Name: idx_sys_log_type; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_log_type ON public.sys_log USING btree (log_type);


--
-- Name: idx_sys_log_user; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_log_user ON public.sys_log USING btree (username);


--
-- Name: idx_sys_login_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_login_status ON public.sys_login_log USING btree (status);


--
-- Name: idx_sys_login_time; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_login_time ON public.sys_login_log USING btree (login_time);


--
-- Name: idx_sys_menu_parent; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_menu_parent ON public.sys_menu USING btree (parent_id);


--
-- Name: idx_sys_oper_log_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_oper_log_status ON public.sys_operation_log USING btree (status);


--
-- Name: idx_sys_oper_log_time; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_oper_log_time ON public.sys_operation_log USING btree (created_at);


--
-- Name: idx_sys_oper_log_title; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_oper_log_title ON public.sys_operation_log USING btree (title);


--
-- Name: idx_sys_post_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_post_code ON public.sys_post USING btree (code);


--
-- Name: idx_sys_user_phone; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_user_phone ON public.sys_user USING btree (phone);


--
-- Name: idx_sys_user_username; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sys_user_username ON public.sys_user USING btree (username);


--
-- Name: idx_user_notice_notice_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_notice_notice_id ON public.sys_user_notice USING btree (notice_id);


--
-- Name: idx_user_notice_user_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_notice_user_id ON public.sys_user_notice USING btree (user_id);


--
-- Name: idx_user_permission_permission_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_permission_permission_id ON public.sys_user_permission USING btree (permission_id);


--
-- Name: idx_user_permission_user_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_permission_user_id ON public.sys_user_permission USING btree (user_id);


--
-- Name: idx_user_post_post_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_post_post_id ON public.sys_user_post USING btree (post_id);


--
-- Name: idx_user_post_user_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_post_user_id ON public.sys_user_post USING btree (user_id);


--
-- Name: idx_user_role_role_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_role_role_id ON public.sys_user_role USING btree (role_id);


--
-- Name: idx_user_role_user_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_role_user_id ON public.sys_user_role USING btree (user_id);


--
-- Name: ems_time_period_price fk2a7o9rx5066t0bxuhqovre0jv; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_time_period_price
    ADD CONSTRAINT fk2a7o9rx5066t0bxuhqovre0jv FOREIGN KEY (price_policy_id) REFERENCES public.ems_price_policy(id);


--
-- Name: sys_menu fk2jrf4gb0gjqi8882gxytpxnhe; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_menu
    ADD CONSTRAINT fk2jrf4gb0gjqi8882gxytpxnhe FOREIGN KEY (parent_id) REFERENCES public.sys_menu(id);


--
-- Name: ems_peak_valley_data fk2xxsklymdmvnetmrqbh4v4qfv; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_peak_valley_data
    ADD CONSTRAINT fk2xxsklymdmvnetmrqbh4v4qfv FOREIGN KEY (meter_point_id) REFERENCES public.ems_meter_point(id);


--
-- Name: ems_peak_valley_data fk5rnu6abd4lmuhx0qomoybvqs7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_peak_valley_data
    ADD CONSTRAINT fk5rnu6abd4lmuhx0qomoybvqs7 FOREIGN KEY (energy_unit_id) REFERENCES public.ems_energy_unit(id);


--
-- Name: ems_cost_policy_binding fk8o2swh51gx7j0nfgnq5t4ut1p; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_cost_policy_binding
    ADD CONSTRAINT fk8o2swh51gx7j0nfgnq5t4ut1p FOREIGN KEY (energy_unit_id) REFERENCES public.ems_energy_unit(id);


--
-- Name: sys_dept fk8wb8qylwmt7squef8hedew4qe; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_dept
    ADD CONSTRAINT fk8wb8qylwmt7squef8hedew4qe FOREIGN KEY (manager_id) REFERENCES public.sys_user(id);


--
-- Name: sys_role_permission fk9q28ewrhntqeipl1t04kh1be7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_role_permission
    ADD CONSTRAINT fk9q28ewrhntqeipl1t04kh1be7 FOREIGN KEY (role_id) REFERENCES public.sys_role(id);


--
-- Name: ems_meter_point fk9raqe49pu25u5l2657351qs6u; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_meter_point
    ADD CONSTRAINT fk9raqe49pu25u5l2657351qs6u FOREIGN KEY (energy_type_id) REFERENCES public.ems_energy_type(id);


--
-- Name: ems_alarm_config fk_alarm_config_meter_point; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_alarm_config
    ADD CONSTRAINT fk_alarm_config_meter_point FOREIGN KEY (meter_point_id) REFERENCES public.ems_meter_point(id);


--
-- Name: ems_price_policy_item fkaujit7a1vuuxapn5k3ki5u3dk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_price_policy_item
    ADD CONSTRAINT fkaujit7a1vuuxapn5k3ki5u3dk FOREIGN KEY (policy_id) REFERENCES public.ems_price_policy(id);


--
-- Name: sys_user fkb3pkx0wbo6o8i8lj0gxr37v1n; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user
    ADD CONSTRAINT fkb3pkx0wbo6o8i8lj0gxr37v1n FOREIGN KEY (dept_id) REFERENCES public.sys_dept(id);


--
-- Name: sys_user_role fkb40xxfch70f5qnyfw8yme1n1s; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_role
    ADD CONSTRAINT fkb40xxfch70f5qnyfw8yme1n1s FOREIGN KEY (user_id) REFERENCES public.sys_user(id);


--
-- Name: sys_permission fkbvx60lch0yucxxb6pm8i4s3r5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_permission
    ADD CONSTRAINT fkbvx60lch0yucxxb6pm8i4s3r5 FOREIGN KEY (module_id) REFERENCES public.sys_module(id);


--
-- Name: ems_energy_cost_record fkcr8m7fqdmugsugc5aec8ofv9a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_cost_record
    ADD CONSTRAINT fkcr8m7fqdmugsugc5aec8ofv9a FOREIGN KEY (energy_type_id) REFERENCES public.ems_energy_type(id);


--
-- Name: sys_dept fkd5ou5hch26i1tk6m8jc4fpirw; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_dept
    ADD CONSTRAINT fkd5ou5hch26i1tk6m8jc4fpirw FOREIGN KEY (parent_id) REFERENCES public.sys_dept(id);


--
-- Name: ems_alarm_record fkde5gs6tyxlc7em2ikb2tjgxh5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_alarm_record
    ADD CONSTRAINT fkde5gs6tyxlc7em2ikb2tjgxh5 FOREIGN KEY (config_id) REFERENCES public.ems_alarm_config(id);


--
-- Name: ems_price_policy fkdm20pwebfqrjy1x2p78lk7s19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_price_policy
    ADD CONSTRAINT fkdm20pwebfqrjy1x2p78lk7s19 FOREIGN KEY (energy_type_id) REFERENCES public.ems_energy_type(id);


--
-- Name: sys_user_permission fke1o35okbrlv0wbsq4o8tw6mbp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_permission
    ADD CONSTRAINT fke1o35okbrlv0wbsq4o8tw6mbp FOREIGN KEY (permission_id) REFERENCES public.sys_permission(id);


--
-- Name: ems_energy_cost_record fke4enn5gcecv26cv6qqw5v67j4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_cost_record
    ADD CONSTRAINT fke4enn5gcecv26cv6qqw5v67j4 FOREIGN KEY (energy_unit_id) REFERENCES public.ems_energy_unit(id);


--
-- Name: ems_meter_point fkeoh88jlbidlicytes7sy48uim; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_meter_point
    ADD CONSTRAINT fkeoh88jlbidlicytes7sy48uim FOREIGN KEY (meter_id) REFERENCES public.ems_meter(id);


--
-- Name: sys_user_role fkhh52n8vd4ny9ff4x9fb8v65qx; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_role
    ADD CONSTRAINT fkhh52n8vd4ny9ff4x9fb8v65qx FOREIGN KEY (role_id) REFERENCES public.sys_role(id);


--
-- Name: ems_energy_data fkhxwy6yrxwkmng9culxcvybt25; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_data
    ADD CONSTRAINT fkhxwy6yrxwkmng9culxcvybt25 FOREIGN KEY (energy_type_id) REFERENCES public.ems_energy_type(id);


--
-- Name: ems_meter fkimy8spwpso0kru9e6k2kr5t74; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_meter
    ADD CONSTRAINT fkimy8spwpso0kru9e6k2kr5t74 FOREIGN KEY (energy_type_id) REFERENCES public.ems_energy_type(id);


--
-- Name: ems_cost_policy_binding fkj65jboyv49b49jsk2v9ibcfn6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_cost_policy_binding
    ADD CONSTRAINT fkj65jboyv49b49jsk2v9ibcfn6 FOREIGN KEY (price_policy_id) REFERENCES public.ems_price_policy(id);


--
-- Name: ems_energy_data fkl2rph682la6pe73bnxopxccwn; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_data
    ADD CONSTRAINT fkl2rph682la6pe73bnxopxccwn FOREIGN KEY (meter_point_id) REFERENCES public.ems_meter_point(id);


--
-- Name: sys_role_dept fkmdoybh4v5t2ooi48m3307n7fx; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_role_dept
    ADD CONSTRAINT fkmdoybh4v5t2ooi48m3307n7fx FOREIGN KEY (role_id) REFERENCES public.sys_role(id);


--
-- Name: sys_user_post fkng2mc7xcmyerevvobtw95bmu9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_post
    ADD CONSTRAINT fkng2mc7xcmyerevvobtw95bmu9 FOREIGN KEY (post_id) REFERENCES public.sys_post(id);


--
-- Name: ems_alarm_config fknguophx1udpdc43j06f6m3wdg; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_alarm_config
    ADD CONSTRAINT fknguophx1udpdc43j06f6m3wdg FOREIGN KEY (limit_type_id) REFERENCES public.ems_alarm_limit_type(id);


--
-- Name: ems_energy_unit fknlkr4e061x5t6wx2u6broi9sd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_unit
    ADD CONSTRAINT fknlkr4e061x5t6wx2u6broi9sd FOREIGN KEY (parent_id) REFERENCES public.ems_energy_unit(id);


--
-- Name: sys_role_permission fkomxrs8a388bknvhjokh440waq; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_role_permission
    ADD CONSTRAINT fkomxrs8a388bknvhjokh440waq FOREIGN KEY (permission_id) REFERENCES public.sys_permission(id);


--
-- Name: sys_user_permission fkoo9oirp69aql5xrde05q948ja; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_permission
    ADD CONSTRAINT fkoo9oirp69aql5xrde05q948ja FOREIGN KEY (user_id) REFERENCES public.sys_user(id);


--
-- Name: sys_user_post fkpjx0gi8xwm66cp1w1jvi4hc57; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sys_user_post
    ADD CONSTRAINT fkpjx0gi8xwm66cp1w1jvi4hc57 FOREIGN KEY (user_id) REFERENCES public.sys_user(id);


--
-- Name: ems_energy_unit_point fkpuo4y0owrgxop99sru6qqbtdf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_unit_point
    ADD CONSTRAINT fkpuo4y0owrgxop99sru6qqbtdf FOREIGN KEY (energy_unit_id) REFERENCES public.ems_energy_unit(id);


--
-- Name: ems_energy_unit_point fksuy4wdg82ye58frkig3ubp606; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_energy_unit_point
    ADD CONSTRAINT fksuy4wdg82ye58frkig3ubp606 FOREIGN KEY (meter_point_id) REFERENCES public.ems_meter_point(id);


--
-- Name: ems_benchmark fkuimhqqp60r0iotcwwu346jel; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ems_benchmark
    ADD CONSTRAINT fkuimhqqp60r0iotcwwu346jel FOREIGN KEY (energy_type_id) REFERENCES public.ems_energy_type(id);


--
-- PostgreSQL database dump complete
--

