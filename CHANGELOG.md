markdown
## 2024-06-21

### Added
- Introduced `wikimedia_producer` module with full configuration and dependencies.
- Added Dockerfile for containerizing the `wikimedia_producer` application.
- Implemented Kafka topic configuration and event handling for Wikimedia changes.
- Created entity classes (`Meta`, `RecentChange`, `Revision`) and their respective repositories.
- Configured application properties for Kafka, PostgreSQL, and management endpoints.
- Added Maven wrapper scripts for Unix and Windows systems.
- Included a test class for `WikimediaProducerApplication`.