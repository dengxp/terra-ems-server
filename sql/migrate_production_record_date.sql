ALTER TABLE ems_production_record ALTER COLUMN record_date TYPE TIMESTAMP WITHOUT TIME ZONE USING record_date::timestamp;
