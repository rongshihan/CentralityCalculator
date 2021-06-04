package Entity;

public class DegreeE {
    String name;
    double idc;//绝对入度中心度
    double odc;//绝对出度中心度
    double nidc;//相对入度中心度
    double nodc;//相对出度中心度

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIdc() {
        return idc;
    }

    public void setIdc(double idc) {
        this.idc = idc;
    }

    public double getOdc() {
        return odc;
    }

    public void setOdc(double odc) {
        this.odc = odc;
    }

    public double getNidc() {
        return nidc;
    }

    public void setNidc(double nidc) {
        this.nidc = nidc;
    }

    public double getNodc() {
        return nodc;
    }

    public void setNodc(double nodc) {
        this.nodc = nodc;
    }
}
