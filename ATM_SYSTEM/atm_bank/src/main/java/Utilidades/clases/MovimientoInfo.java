package Utilidades.clases;

import java.math.BigDecimal;

public class MovimientoInfo {
    private int id_movimiento;
    private String tipoMovimiento;
    private BigDecimal cantidad;
    private String fechaMovimiento;
    private String descripcion;
    private String remitente;
    private String destinatario;

    public MovimientoInfo() {
    }

    public MovimientoInfo(int id_movimiento, String tipoMovimiento, BigDecimal cantidad, String fechaMovimiento, String descripcion, String remitente, String destinatario) {
        this.id_movimiento = id_movimiento;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.fechaMovimiento = fechaMovimiento;
        this.descripcion = descripcion;
        this.remitente = remitente;
        this.destinatario = destinatario;
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public String toString() {
        return "MovimientoInfo{" + "id_movimiento=" + id_movimiento + ", tipoMovimiento=" + tipoMovimiento + ", cantidad=" + cantidad + ", fechaMovimiento=" + fechaMovimiento + ", descripcion=" + descripcion + ", remitente=" + remitente + ", destinatario=" + destinatario + '}';
    }
    
}
