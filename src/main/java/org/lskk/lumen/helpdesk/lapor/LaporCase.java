package org.lskk.lumen.helpdesk.lapor;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Created by ceefour on 02/08/2016.
 */
@Document(createIndex = false, indexName = "logstash-2016.07.27")
public class LaporCase implements Serializable {
    @Id
    private String id;
    @JsonProperty("@version")
    private String version;
    @JsonProperty("@timestamp")
    private OffsetDateTime timestamp;
    private String message;
    private String path;
    private String host;
    private String type;
    @JsonProperty("TrackingID")
    private String trackingId;
    @JsonProperty("Pelapor")
    private String reporter;
    @JsonProperty("JudulLaporan")
    private String title;
    @JsonProperty("IsiLaporan")
    private String body;
    @JsonProperty("Kategori")
    private String category;
    @JsonProperty("KategoriID")
    private String categoryId;
    @JsonProperty("DisposisiInstansi")
    private String institutionDisposition;
    @JsonProperty("DisposisiInstansiID")
    private String institutionDispositionId;
    @JsonProperty("Area")
    private String area;
    @JsonProperty("AreID")
    private String areaId;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("TanggalDisposisi")
    private String dispositionTime;
    @JsonProperty("TanggalLaporanMasuk")
    private String reportCreationTime;
    @JsonProperty("TanggalLaporanAktivitasTerakhir")
    private String reportLastActivityTime;
    @JsonProperty("TanggalLaporanDitutup")
    private String reportClosureTime;
    @JsonProperty("TimestampLaporanMasuk")
    private Long reportCreationTimestamp;
    @JsonProperty("TimestampTanggalDisposisi")
    private Long dispositionTimestamp;
    @JsonProperty("TimestampTanggalAktivitasTerakhir")
    private Long reportLastActivityTimestamp;
    @JsonProperty("TimestampTanggalLaporanDitutup")
    private Long reportClosureTimestamp;
    @JsonProperty("created_at")
    private String creationTime;
    @JsonProperty("updated_at")
    private String modificationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHost() {
        return host;
    }

    public void setHost( String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getInstitutionDisposition() {
        return institutionDisposition;
    }

    public void setInstitutionDisposition(String institutionDisposition) {
        this.institutionDisposition = institutionDisposition;
    }

    public String getInstitutionDispositionId() {
        return institutionDispositionId;
    }

    public void setInstitutionDispositionId(String institutionDispositionId) {
        this.institutionDispositionId = institutionDispositionId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDispositionTime() {
        return dispositionTime;
    }

    public void setDispositionTime(String dispositionTime) {
        this.dispositionTime = dispositionTime;
    }

    public String getReportCreationTime() {
        return reportCreationTime;
    }

    public void setReportCreationTime(String reportCreationTime) {
        this.reportCreationTime = reportCreationTime;
    }

    public String getReportLastActivityTime() {
        return reportLastActivityTime;
    }

    public void setReportLastActivityTime(String reportLastActivityTime) {
        this.reportLastActivityTime = reportLastActivityTime;
    }

    public String getReportClosureTime() {
        return reportClosureTime;
    }

    public void setReportClosureTime(String reportClosureTime) {
        this.reportClosureTime = reportClosureTime;
    }

    public Long getReportCreationTimestamp() {
        return reportCreationTimestamp;
    }

    public void setReportCreationTimestamp(Long reportCreationTimestamp) {
        this.reportCreationTimestamp = reportCreationTimestamp;
    }

    public Long getDispositionTimestamp() {
        return dispositionTimestamp;
    }

    public void setDispositionTimestamp(Long dispositionTimestamp) {
        this.dispositionTimestamp = dispositionTimestamp;
    }

    public Long getReportLastActivityTimestamp() {
        return reportLastActivityTimestamp;
    }

    public void setReportLastActivityTimestamp(Long reportLastActivityTimestamp) {
        this.reportLastActivityTimestamp = reportLastActivityTimestamp;
    }

    public Long getReportClosureTimestamp() {
        return reportClosureTimestamp;
    }

    public void setReportClosureTimestamp(Long reportClosureTimestamp) {
        this.reportClosureTimestamp = reportClosureTimestamp;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }
}
