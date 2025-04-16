ALTER TABLE activities ADD COLUMN activity_timestamp TIMESTAMP;

-- Update the timestamp column with values from date column
-- For existing entries, we preserve the original date and set time to noon
UPDATE activities
SET activity_timestamp = TIMESTAMP(activity_date);

-- Drop the existing date column
ALTER TABLE activities DROP COLUMN activity_date;

-- Rename timestamp column to activity_date
ALTER TABLE activities CHANGE COLUMN activity_timestamp activity_date TIMESTAMP NOT NULL;

-- Add index for performance reasons
CREATE INDEX idx_activity_date ON activities(activity_date);
