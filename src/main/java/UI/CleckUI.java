/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import controller.KhachHangJpaController;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.TienPhatDAO;
import dao.TieuDeDAO;
import dao.cthdDAO;
import dao.dvdDAO;
import entity.DatTruoc;
import entity.HoaDon;
import entity.KhachHang;
import entity.TienPhat;
import entity.TieuDe;
import entity.ChiTietHoaDon;
import entity.Dvd;
import entity.Loai;
import java.awt.Frame;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Octob
 */
public class CleckUI extends javax.swing.JFrame {

    /**
     * Creates new form CleckUI
     */
    //Giang - Biến di chuyển JFrame (Undecorated)
    static int moveX;
    static int moveY;

    static int moveXDal;
    static int moveYDal;
    //Thịnh-DAO khách hàng
    KhachHangDAO khdao = new KhachHangDAO();
    TieuDeDAO tddao = new TieuDeDAO();
    
    static boolean statusLogin = false;
    
    //Giang - Khai báo biến Model cho JList
    DefaultListModel<TieuDe> modelTieuDe = new DefaultListModel<TieuDe>();
    
    DefaultComboBoxModel<String> comboboxModelTenTD=new DefaultComboBoxModel<>();
    DefaultListModel<KhachHang> modelKhachHang = new DefaultListModel<KhachHang>();
    DefaultListModel<Dvd> modelDVD = new DefaultListModel<Dvd>();
    DefaultListModel<Dvd> modelDVDThue = new DefaultListModel<Dvd>();
    DefaultListModel<HoaDon> modelTraDia = new DefaultListModel<HoaDon>();
    DefaultListModel<ChiTietHoaDon> modelCTHD = new DefaultListModel<ChiTietHoaDon>();
    DefaultListModel<DatTruoc> modelDatTruoc = new DefaultListModel<DatTruoc>();
    DefaultListModel<TienPhat> modelTienPhat = new DefaultListModel<TienPhat>();

    //Giang - Định dạng chung cho in ngày
    Format ft_ngay = new SimpleDateFormat("dd-MM-yyyy");

    //Giang - List Tiêu Đề - DATABASE
    ArrayList<TieuDe> listTD = new TieuDeDAO().getListTD();
    
     
    //Giang - List Khách Hàng - DATABASE
    ArrayList<KhachHang> listKH = new KhachHangDAO().listKH();

    //Giang - List DVD - DATABASE
    ArrayList<Dvd> listDVD = new dvdDAO().getListDVD();

    //Giang - List CHTD - DATABASE
    ArrayList<ChiTietHoaDon> listCTHD = new cthdDAO().getListCTHD();

    //Giang - List HD - DATABASE
    ArrayList<HoaDon> listHoaDon = new HoaDonDAO().getListHoaDon();

    //Giang - List Dat Truoc - DATABASE
    ArrayList<DatTruoc> listDatTruoc = new ArrayList<>();

    //Giang - List Tien Phat - DATABASE
    ArrayList<TienPhat> listTienPhat = new TienPhatDAO().getListTienPhat();

    //Giang - List Loai - DATABASE
    //ArrayList<LoaiTieuDe> listLoai = new ArrayList<>();

    public CleckUI() {
        initComponents();
        customComponents();
    }
    
    public static void setLogin(boolean tt){
        if(!tt){
            jLabel1.hide();
            pnl_btn_QuanLyKhoDia.hide();
            pnl_btn_QuanLyTieuDe.hide();
            popChangeStatus1.hide();
        }else{
            jLabel1.show();
            pnl_btn_QuanLyKhoDia.show();
            pnl_btn_QuanLyTieuDe.show();
            popChangeStatus1.show();
        }
    }

    //Giang - Custom Code - GIANG
    public void customComponents() {
        setLogin(statusLogin);
        //Show Data ra JList 
        showTitle();
        showKhachHang();
        showDVD(listDVD);

        //Set action for Button Slidebar
        actionChangeHover(btnQuanLyChoThue);

        //Center Dialog
        dal_ChinhSuaTieuDe.setLocationRelativeTo(null);
        dal_ChinhSuaKhachHang.setLocationRelativeTo(null);
        dal_PhiTre.setLocationRelativeTo(null);
    }

    //Giang - Hover Panel Button
    public void actionChangeHover(JButton btn) {
        ArrayList<JButton> listBtn = new ArrayList<>();
        listBtn.add(btnQuanLyChoThue);
        listBtn.add(btnQuanLyKhachHang);
        listBtn.add(btnQuanLyTraDia);
        listBtn.add(btnQuanLyDatTruoc);
        listBtn.add(btnQuanLyChiPhi);
        listBtn.add(btnQuanLyTieuDe);
        listBtn.add(btnQuanLyKhoDia);

        ArrayList<JPanel> listPanelHighList = new ArrayList<>();
        listPanelHighList.add(pnl_hl1);
        listPanelHighList.add(pnl_hl2);
        listPanelHighList.add(pnl_hl3);
        listPanelHighList.add(pnl_hl4);
        listPanelHighList.add(pnl_hl5);
        listPanelHighList.add(pnl_hl6);
        listPanelHighList.add(pnl_hl7);

        ArrayList<JPanel> listPanelContent = new ArrayList<>();
        listPanelContent.add(pnlQuanLyChoThue);
        listPanelContent.add(pnlQuanLyKhachHang);
        listPanelContent.add(pnlQuanLyTraDia);
        listPanelContent.add(pnlQuanLyDatTruoc);
        listPanelContent.add(pnlQuanLyChiphi);
        listPanelContent.add(pnlQuanLyTieuDe);
        listPanelContent.add(pnlQuanLyKhoDia);

        ArrayList<String> listImg = new ArrayList<>();
        listImg.add("/UI/img/test.png");
        listImg.add("/UI/img/user.png");
        listImg.add("/UI/img/reply.png");
        listImg.add("/UI/img/download-to-storage-drive.png");
        listImg.add("/UI/img/dollar.png");
        listImg.add("/UI/img/tittle.png");
        listImg.add("/UI/img/dvd.png");

        ArrayList<String> listImgHl = new ArrayList<>();
        listImgHl.add("/UI/img/test_hl.png");
        listImgHl.add("/UI/img/user (1).png");
        listImgHl.add("/UI/img/reply (1).png");
        listImgHl.add("/UI/img/download-to-storage-drive (1).png");
        listImgHl.add("/UI/img/dollar (1).png");
        listImgHl.add("/UI/img/tittle1.png");
        listImgHl.add("/UI/img/dvd1.png");

        for (int i = 0; i < 7; i++) {
            if (btn.getText().equals(listBtn.get(i).getText())) {
                listBtn.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource(listImgHl.get(i))));
                btn.setForeground(new java.awt.Color(255, 102, 51));
                listPanelHighList.get(i).setBackground(new java.awt.Color(255, 102, 51));
                listPanelContent.get(i).show();
            } else {
                listBtn.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource(listImg.get(i))));
                listBtn.get(i).setForeground(new java.awt.Color(0, 0, 0));
                listPanelHighList.get(i).setBackground(new java.awt.Color(255, 255, 255));
                listPanelContent.get(i).hide();
            }
        }

    }

    //Giang - Hàm xuất danh sách phí trễ hạn của KH
    public ArrayList<TienPhat> getList_TienPhat_formMaKH(String maKH) {
        ArrayList<TienPhat> listTP = new ArrayList<>();
        for (int i = 0; i < listCTHD.size(); i++) {
            if (listCTHD.get(i).getHdMa().getKhMa().getKhMa().equalsIgnoreCase(maKH)) {
                listTP.add(listCTHD.get(i).getPtMa());
            }
        }
        return listTP;
    }

    //Giang - Show TienPhat to JList
    public void showTienPhat(String maKH) {
        modelCTHD.removeAllElements();
        for (int i = 0; i < listCTHD.size(); i++) {
            if (listCTHD.get(i).getHdMa().getKhMa().getKhMa().equalsIgnoreCase(txtSearchKH_Thue.getText())) {
                modelCTHD.addElement(listCTHD.get(i));
            }
        }
        list_PhiTreHanThue.setModel(modelCTHD);
        list_PhiTreHanThue.setCellRenderer(new formTienPhat());
    }

    //Giang - Show Tittle to JList -> Thịnh đã sửa
    public void showTitle() {
        listTD = tddao.getListTD();
        modelTieuDe.removeAllElements();
        for (int i = 0; i < listTD.size(); i++) {
            modelTieuDe.addElement(listTD.get(i));
        }
        list_TieuDe_Manager.setModel(modelTieuDe);
        list_TieuDe_Manager.setCellRenderer(new formTieuDe());
    }

    //Giang - Show KhachHang to JList ->Thịnh đã sửa
    public void showKhachHang() {
        listKH = khdao.listKH();
        modelKhachHang.removeAllElements();

        for (int i = 0; i < listKH.size(); i++) {
            modelKhachHang.addElement(listKH.get(i));
        }
        list_KhachHang.setModel(modelKhachHang);
        list_KhachHang.setCellRenderer(new formKhachHang());
    }

    //Giang -show DVD to JList JList
    public void showDVD(ArrayList<Dvd> list) {
         
        modelDVD.removeAllElements();

        for (int i = 0; i < listDVD.size(); i++) {
            modelDVD.addElement(listDVD.get(i));
        }
        list_DVD.setModel(modelDVD);
        list_DVD.setCellRenderer(new formDVD());
    }

    //Giang - search TieuDe form String
    public void searchTieuDe(JTextField txt) {
        //Get listTieuDe
        ArrayList<TieuDe> listTD_loc = new ArrayList<>();
        for (int i = 0; i < listTD.size(); i++) {
            if (listTD.get(i).getTdTenTD().matches(".*" + txt.getText() + ".*")) {
                listTD_loc.add(listTD.get(i));
            }
        }
        showTitle();
    }

    //Giang - kiem tra phann tu chung 2 list dvd
    public boolean checkTrungListDVD(String idDVD, ArrayList<Dvd> list) {
        boolean kq = true;
        if (list.size() == 0) {
            kq = true;
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getDvdMa().equalsIgnoreCase(idDVD)) {
                    kq = false;
                }
            }
        }
        return kq;
    }

    //Giang - clear Data thue
    public void deleteValueThue() {
        txtSearchKH_Thue.setText("");
        txtSearchIDDVD.setText("");
        lb_infoMaKH.setText("");
        lb_infoTenKH.setText("");
        lb_TongTien.setText("");
        modelDVDThue.removeAllElements();
        modelCTHD.removeAllElements();
    }

    //Giang - ham tinh tong tien
    public void tinhTongTien() {
        double sum = 0;
        for (int i = 0; i < list_DVDtemp.getModel().getSize(); i++) {
            sum += list_DVDtemp.getModel().getElementAt(i).getTdMa().getLoaiMa().getLoaiGia();
        }
        int[] selectedIx = list_PhiTreHanThue.getSelectedIndices();
        for (int i = 0; i < selectedIx.length; i++) {
            sum += list_PhiTreHanThue.getModel().getElementAt(selectedIx[i]).getPtMa().getPtTienPhat();
        }
        DecimalFormat ft_tien = new DecimalFormat("###,###,###" + " VNĐ");
        lb_TongTien.setText(ft_tien.format(sum));
    }
    
    //kiểm tra chuỗi nhập
    private boolean kiemTraChuoi(String x, String quytac) {

        Pattern pt = Pattern.compile(quytac);
        Matcher ma = pt.matcher(x);
        if (ma.matches()) {
            return true;
        }
        return false;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MoOption_TieuDe = new javax.swing.JPopupMenu();
        popChinhSua = new javax.swing.JMenuItem();
        popChangeStatus = new javax.swing.JMenuItem();
        dal_ChinhSuaTieuDe = new javax.swing.JDialog();
        dal_Container = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtMaTD = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtTenTD = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtNhaSX = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtNgaySX = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtHinhAnh = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnHinhAnh = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cbbDVD = new javax.swing.JComboBox<>();
        dal_File = new javax.swing.JFileChooser();
        dal_DVD = new javax.swing.JDialog();
        DVD_Container = new javax.swing.JPanel();
        MoOption_KhachHang = new javax.swing.JPopupMenu();
        popChinhSua1 = new javax.swing.JMenuItem();
        popChangeStatus1 = new javax.swing.JMenuItem();
        dal_ChinhSuaKhachHang = new javax.swing.JDialog();
        dal_Container1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        txtTenKH = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        txtSDT = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        cb_TTKH = new javax.swing.JCheckBox();
        dal_PhiTre = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        list_PhiTreHanThue = new javax.swing.JList<>();
        jLabel23 = new javax.swing.JLabel();
        btn_CheckKH2 = new javax.swing.JButton();
        MoOption_DVDThue = new javax.swing.JPopupMenu();
        popLoaiBo = new javax.swing.JMenuItem();
        dal_ThemKH = new javax.swing.JDialog();
        dal_Container2 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        txtTenKHThem = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        txtDiaChiThem = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        txtSDTThem = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        btn_LuuThemKH = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        dal_ThemTD = new javax.swing.JDialog();
        dal_Container4 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        txtTenTD1 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        txtNhaSX1 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        txtNgaySX1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        txtHinhAnh1 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        btnHinhAnh1 = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cbbDVD1 = new javax.swing.JComboBox<>();
        them_DVD = new javax.swing.JDialog();
        dal_Container5 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        txtMaDVD = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel47 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        cbTenTieuDe = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        container = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        pnl_btn_QuanLyChoThue = new javax.swing.JPanel();
        btnQuanLyChoThue = new javax.swing.JButton();
        pnl_hl1 = new javax.swing.JPanel();
        pnl_sp1 = new javax.swing.JPanel();
        pnl_btn_QuanLyKhachHang = new javax.swing.JPanel();
        btnQuanLyKhachHang = new javax.swing.JButton();
        pnl_hl2 = new javax.swing.JPanel();
        pnl_sp2 = new javax.swing.JPanel();
        pnl_btn_QuanLyTraDia = new javax.swing.JPanel();
        btnQuanLyTraDia = new javax.swing.JButton();
        pnl_hl3 = new javax.swing.JPanel();
        pnl_sp3 = new javax.swing.JPanel();
        pnl_btn_QuanLyDatTruoc = new javax.swing.JPanel();
        btnQuanLyDatTruoc = new javax.swing.JButton();
        pnl_hl4 = new javax.swing.JPanel();
        pnl_sp4 = new javax.swing.JPanel();
        pnl_btn_QuanLyChiPhi = new javax.swing.JPanel();
        btnQuanLyChiPhi = new javax.swing.JButton();
        pnl_hl5 = new javax.swing.JPanel();
        pnl_sp5 = new javax.swing.JPanel();
        pnl_btn_QuanLyTieuDe = new javax.swing.JPanel();
        btnQuanLyTieuDe = new javax.swing.JButton();
        pnl_hl6 = new javax.swing.JPanel();
        pnl_sp6 = new javax.swing.JPanel();
        pnl_btn_QuanLyKhoDia = new javax.swing.JPanel();
        btnQuanLyKhoDia = new javax.swing.JButton();
        pnl_hl7 = new javax.swing.JPanel();
        pnl_sp7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        DangNhap = new javax.swing.JButton();
        sdMenu = new javax.swing.JLabel();
        codated = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        lbTitle = new javax.swing.JLabel();
        btnClose1 = new javax.swing.JButton();
        container_Content = new javax.swing.JPanel();
        pnlQuanLyChoThue = new javax.swing.JPanel();
        lb_TongTien = new javax.swing.JLabel();
        txtSearchIDDVD = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSearchKH_Thue = new javax.swing.JTextField();
        btn_CheckKH = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btn_CheckDVD = new javax.swing.JButton();
        btnSearch4 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        list_DVDtemp = new javax.swing.JList<>();
        lb_infoTenKH = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        btn_CheckKH1 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        lb_infoMaKH = new javax.swing.JLabel();
        txtSoNgayMuon = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        pnlQuanLyKhachHang = new javax.swing.JPanel();
        btnSearch_KH = new javax.swing.JButton();
        txtSearch_KH = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        list_KhachHang = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        btnThemKH = new javax.swing.JButton();
        pnlQuanLyTraDia = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        list_TraDia = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        txtSearch_KH1 = new javax.swing.JTextField();
        btnSearch_KH2 = new javax.swing.JButton();
        pnlQuanLyDatTruoc = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        list_TraDia1 = new javax.swing.JList<>();
        jLabel22 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        txtSearch_KH2 = new javax.swing.JTextField();
        btnSearch_KH3 = new javax.swing.JButton();
        pnlQuanLyChiphi = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pnlQuanLyTieuDe = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_TieuDe_Manager = new javax.swing.JList<>();
        jLabel15 = new javax.swing.JLabel();
        txtSearch1 = new javax.swing.JTextField();
        btnSearch1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        btnThemTD = new javax.swing.JButton();
        pnlQuanLyKhoDia = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        list_DVD = new javax.swing.JList<>();
        jPanel17 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtSearch_DVD = new javax.swing.JTextField();
        btnSearch_KH1 = new javax.swing.JButton();
        btnThemDVD = new javax.swing.JButton();

        MoOption_TieuDe.setBackground(new java.awt.Color(51, 51, 51));
        MoOption_TieuDe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        MoOption_TieuDe.setLightWeightPopupEnabled(false);

        popChinhSua.setBackground(new java.awt.Color(255, 255, 255));
        popChinhSua.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        popChinhSua.setText("Chỉnh sửa");
        popChinhSua.setMargin(new java.awt.Insets(10, 10, 10, 20));
        popChinhSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popChinhSuaActionPerformed(evt);
            }
        });
        MoOption_TieuDe.add(popChinhSua);

        popChangeStatus.setBackground(new java.awt.Color(255, 255, 255));
        popChangeStatus.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        popChangeStatus.setText("Thay đổi trạng thái");
        popChangeStatus.setMargin(new java.awt.Insets(10, 10, 10, 20));
        popChangeStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popChangeStatusActionPerformed(evt);
            }
        });
        MoOption_TieuDe.add(popChangeStatus);

        dal_ChinhSuaTieuDe.setResizable(false);
        dal_ChinhSuaTieuDe.setSize(new java.awt.Dimension(400, 600));

        dal_Container.setBackground(new java.awt.Color(255, 255, 255));
        dal_Container.setForeground(new java.awt.Color(255, 255, 255));
        dal_Container.setPreferredSize(new java.awt.Dimension(400, 600));
        dal_Container.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Mã tiêu đề:");
        dal_Container.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 100, 20));

        txtMaTD.setEditable(false);
        txtMaTD.setBackground(new java.awt.Color(255, 255, 255));
        txtMaTD.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtMaTD.setForeground(new java.awt.Color(255, 102, 51));
        txtMaTD.setText("1");
        txtMaTD.setBorder(null);
        txtMaTD.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container.add(txtMaTD, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel3.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jPanel4.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        txtTenTD.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtTenTD.setForeground(new java.awt.Color(255, 102, 51));
        txtTenTD.setText("ABC");
        txtTenTD.setBorder(null);
        txtTenTD.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container.add(txtTenTD, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Tên tiêu đề:");
        dal_Container.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 100, 20));

        jPanel5.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        txtNhaSX.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtNhaSX.setForeground(new java.awt.Color(255, 102, 51));
        txtNhaSX.setText("ABC");
        txtNhaSX.setBorder(null);
        txtNhaSX.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container.add(txtNhaSX, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Nhà sản xuất:");
        dal_Container.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 100, 20));

        jPanel6.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, -1, -1));

        txtNgaySX.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtNgaySX.setForeground(new java.awt.Color(255, 102, 51));
        txtNgaySX.setText("22/11/1999");
        txtNgaySX.setBorder(null);
        txtNgaySX.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container.add(txtNgaySX, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Ngày sản xuất:");
        dal_Container.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 100, 20));

        jPanel7.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, -1, -1));

        txtHinhAnh.setEditable(false);
        txtHinhAnh.setBackground(new java.awt.Color(255, 255, 255));
        txtHinhAnh.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtHinhAnh.setForeground(new java.awt.Color(255, 102, 51));
        txtHinhAnh.setText("doreamon.jpg");
        txtHinhAnh.setBorder(null);
        txtHinhAnh.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container.add(txtHinhAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 490, 240, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Hình ảnh:");
        dal_Container.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, 100, 20));

        jPanel8.setBackground(new java.awt.Color(255, 102, 51));

        btnHinhAnh.setContentAreaFilled(false);
        btnHinhAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinhAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnHinhAnh)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnHinhAnh)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        dal_Container.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 490, 50, 40));

        jPanel9.setBackground(new java.awt.Color(255, 102, 51));

        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Thêm");
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dal_Container.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 550, 100, 40));

        jPanel10.setBackground(new java.awt.Color(255, 102, 51));
        jPanel10.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel10MouseDragged(evt);
            }
        });
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel10MousePressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Chỉnh sửa thông tin tiêu đề");
        jLabel9.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel9MouseDragged(evt);
            }
        });
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel9MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        dal_Container.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 50));

        jLabel31.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 51));
        jLabel31.setText("Nhà sản xuất:");
        dal_Container.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 100, 20));

        cbbDVD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phim", "Game" }));
        dal_Container.add(cbbDVD, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 130, 30));

        javax.swing.GroupLayout dal_ChinhSuaTieuDeLayout = new javax.swing.GroupLayout(dal_ChinhSuaTieuDe.getContentPane());
        dal_ChinhSuaTieuDe.getContentPane().setLayout(dal_ChinhSuaTieuDeLayout);
        dal_ChinhSuaTieuDeLayout.setHorizontalGroup(
            dal_ChinhSuaTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dal_Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        dal_ChinhSuaTieuDeLayout.setVerticalGroup(
            dal_ChinhSuaTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dal_ChinhSuaTieuDeLayout.createSequentialGroup()
                .addComponent(dal_Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        DVD_Container.setBackground(new java.awt.Color(255, 255, 255));
        DVD_Container.setPreferredSize(new java.awt.Dimension(600, 400));

        javax.swing.GroupLayout DVD_ContainerLayout = new javax.swing.GroupLayout(DVD_Container);
        DVD_Container.setLayout(DVD_ContainerLayout);
        DVD_ContainerLayout.setHorizontalGroup(
            DVD_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        DVD_ContainerLayout.setVerticalGroup(
            DVD_ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout dal_DVDLayout = new javax.swing.GroupLayout(dal_DVD.getContentPane());
        dal_DVD.getContentPane().setLayout(dal_DVDLayout);
        dal_DVDLayout.setHorizontalGroup(
            dal_DVDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DVD_Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        dal_DVDLayout.setVerticalGroup(
            dal_DVDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DVD_Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        MoOption_KhachHang.setBackground(new java.awt.Color(51, 51, 51));
        MoOption_KhachHang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        MoOption_KhachHang.setLightWeightPopupEnabled(false);

        popChinhSua1.setBackground(new java.awt.Color(255, 255, 255));
        popChinhSua1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        popChinhSua1.setText("Chỉnh sửa");
        popChinhSua1.setMargin(new java.awt.Insets(10, 10, 10, 20));
        popChinhSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popChinhSua1ActionPerformed(evt);
            }
        });
        MoOption_KhachHang.add(popChinhSua1);

        popChangeStatus1.setBackground(new java.awt.Color(255, 255, 255));
        popChangeStatus1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        popChangeStatus1.setText("Thay đổi trạng thái");
        popChangeStatus1.setMargin(new java.awt.Insets(10, 10, 10, 20));
        popChangeStatus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popChangeStatus1ActionPerformed(evt);
            }
        });
        MoOption_KhachHang.add(popChangeStatus1);

        dal_ChinhSuaKhachHang.setResizable(false);
        dal_ChinhSuaKhachHang.setSize(new java.awt.Dimension(400, 600));

        dal_Container1.setBackground(new java.awt.Color(255, 255, 255));
        dal_Container1.setForeground(new java.awt.Color(255, 255, 255));
        dal_Container1.setPreferredSize(new java.awt.Dimension(400, 600));
        dal_Container1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Mã KH:");
        dal_Container1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 100, 20));

        txtMaKH.setEditable(false);
        txtMaKH.setBackground(new java.awt.Color(255, 255, 255));
        txtMaKH.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtMaKH.setForeground(new java.awt.Color(255, 102, 51));
        txtMaKH.setText("1");
        txtMaKH.setBorder(null);
        txtMaKH.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container1.add(txtMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel13.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jPanel14.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        txtTenKH.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtTenKH.setForeground(new java.awt.Color(255, 102, 51));
        txtTenKH.setText("Oanh");
        txtTenKH.setBorder(null);
        txtTenKH.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container1.add(txtTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Tên khách hàng");
        dal_Container1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 100, 20));

        jPanel15.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        txtDiaChi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtDiaChi.setForeground(new java.awt.Color(255, 102, 51));
        txtDiaChi.setText("Nghe An");
        txtDiaChi.setBorder(null);
        txtDiaChi.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container1.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Địa chỉ");
        dal_Container1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 100, 20));

        jPanel16.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        txtSDT.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSDT.setForeground(new java.awt.Color(255, 102, 51));
        txtSDT.setText("22/11/1999");
        txtSDT.setBorder(null);
        txtSDT.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container1.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, -1, -1));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Số điện thoại");
        dal_Container1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 100, 20));

        jPanel19.setBackground(new java.awt.Color(255, 102, 51));

        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Thêm");
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        dal_Container1.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 470, 100, 40));

        jPanel20.setBackground(new java.awt.Color(255, 102, 51));
        jPanel20.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel20MouseDragged(evt);
            }
        });
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel20MousePressed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Chỉnh sửa thông tin khách hàng");
        jLabel21.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel21MouseDragged(evt);
            }
        });
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel21MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        dal_Container1.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 50));

        cb_TTKH.setBackground(new java.awt.Color(255, 255, 255));
        cb_TTKH.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cb_TTKH.setForeground(new java.awt.Color(255, 102, 51));
        cb_TTKH.setText("Hoạt động");
        dal_Container1.add(cb_TTKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, -1, -1));

        javax.swing.GroupLayout dal_ChinhSuaKhachHangLayout = new javax.swing.GroupLayout(dal_ChinhSuaKhachHang.getContentPane());
        dal_ChinhSuaKhachHang.getContentPane().setLayout(dal_ChinhSuaKhachHangLayout);
        dal_ChinhSuaKhachHangLayout.setHorizontalGroup(
            dal_ChinhSuaKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dal_Container1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        dal_ChinhSuaKhachHangLayout.setVerticalGroup(
            dal_ChinhSuaKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dal_ChinhSuaKhachHangLayout.createSequentialGroup()
                .addComponent(dal_Container1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        dal_PhiTre.setBackground(new java.awt.Color(255, 255, 255));
        dal_PhiTre.setResizable(false);
        dal_PhiTre.setSize(new java.awt.Dimension(730, 500));
        dal_PhiTre.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                dal_PhiTreWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                dal_PhiTreWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(730, 500));

        jScrollPane7.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane7.setBorder(null);
        jScrollPane7.setPreferredSize(new java.awt.Dimension(730, 500));

        list_PhiTreHanThue.setBackground(new java.awt.Color(241, 245, 246));
        list_PhiTreHanThue.setPreferredSize(new java.awt.Dimension(730, 500));
        jScrollPane7.setViewportView(list_PhiTreHanThue);

        jLabel23.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 51, 51));
        jLabel23.setText("Click chọn vào 1 hoặc nhiều dòng muốn thanh toán");

        btn_CheckKH2.setBackground(new java.awt.Color(51, 51, 51));
        btn_CheckKH2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_CheckKH2.setForeground(new java.awt.Color(51, 51, 51));
        btn_CheckKH2.setText("Xác nhận");
        btn_CheckKH2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_CheckKH2.setContentAreaFilled(false);
        btn_CheckKH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CheckKH2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                .addComponent(btn_CheckKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(455, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_CheckKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 80, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout dal_PhiTreLayout = new javax.swing.GroupLayout(dal_PhiTre.getContentPane());
        dal_PhiTre.getContentPane().setLayout(dal_PhiTreLayout);
        dal_PhiTreLayout.setHorizontalGroup(
            dal_PhiTreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dal_PhiTreLayout.setVerticalGroup(
            dal_PhiTreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MoOption_DVDThue.setBackground(new java.awt.Color(51, 51, 51));
        MoOption_DVDThue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        MoOption_DVDThue.setLightWeightPopupEnabled(false);

        popLoaiBo.setBackground(new java.awt.Color(255, 255, 255));
        popLoaiBo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        popLoaiBo.setText("Loại bỏ");
        popLoaiBo.setMargin(new java.awt.Insets(10, 10, 10, 20));
        popLoaiBo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popLoaiBoActionPerformed(evt);
            }
        });
        MoOption_DVDThue.add(popLoaiBo);

        dal_ThemKH.setLocationByPlatform(true);
        dal_ThemKH.setResizable(false);
        dal_ThemKH.setSize(new java.awt.Dimension(400, 600));

        dal_Container2.setBackground(new java.awt.Color(255, 255, 255));
        dal_Container2.setForeground(new java.awt.Color(255, 255, 255));
        dal_Container2.setPreferredSize(new java.awt.Dimension(400, 600));
        dal_Container2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel24.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container2.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        txtTenKHThem.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtTenKHThem.setForeground(new java.awt.Color(255, 102, 51));
        txtTenKHThem.setText("Oanh");
        txtTenKHThem.setBorder(null);
        txtTenKHThem.setPreferredSize(new java.awt.Dimension(300, 40));
        txtTenKHThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKHThemActionPerformed(evt);
            }
        });
        dal_Container2.add(txtTenKHThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jLabel27.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setText("Tên khách hàng");
        dal_Container2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 100, 20));

        jPanel25.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container2.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        txtDiaChiThem.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtDiaChiThem.setForeground(new java.awt.Color(255, 102, 51));
        txtDiaChiThem.setText("Nghe An");
        txtDiaChiThem.setBorder(null);
        txtDiaChiThem.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container2.add(txtDiaChiThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText("Địa chỉ");
        dal_Container2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 100, 20));

        jPanel26.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container2.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        txtSDTThem.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSDTThem.setForeground(new java.awt.Color(255, 102, 51));
        txtSDTThem.setText("0799163612");
        txtSDTThem.setBorder(null);
        txtSDTThem.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container2.add(txtSDTThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, -1, -1));

        jLabel29.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 51));
        jLabel29.setText("Số điện thoại");
        dal_Container2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 100, 20));

        jPanel27.setBackground(new java.awt.Color(255, 102, 51));

        btn_LuuThemKH.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_LuuThemKH.setForeground(new java.awt.Color(255, 255, 255));
        btn_LuuThemKH.setActionCommand("Lưu");
        btn_LuuThemKH.setContentAreaFilled(false);
        btn_LuuThemKH.setLabel("Lưu");
        btn_LuuThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LuuThemKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_LuuThemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_LuuThemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        dal_Container2.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 470, 100, 40));

        jPanel28.setBackground(new java.awt.Color(255, 102, 51));
        jPanel28.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel28MouseDragged(evt);
            }
        });
        jPanel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel28MousePressed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Thêm Thông Tin Khách Hàng");
        jLabel30.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel30MouseDragged(evt);
            }
        });
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel30MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        dal_Container2.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 50));

        javax.swing.GroupLayout dal_ThemKHLayout = new javax.swing.GroupLayout(dal_ThemKH.getContentPane());
        dal_ThemKH.getContentPane().setLayout(dal_ThemKHLayout);
        dal_ThemKHLayout.setHorizontalGroup(
            dal_ThemKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dal_Container2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        dal_ThemKHLayout.setVerticalGroup(
            dal_ThemKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dal_ThemKHLayout.createSequentialGroup()
                .addComponent(dal_Container2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        dal_ThemTD.setLocationByPlatform(true);
        dal_ThemTD.setResizable(false);
        dal_ThemTD.setSize(new java.awt.Dimension(400, 600));

        dal_Container4.setBackground(new java.awt.Color(255, 255, 255));
        dal_Container4.setForeground(new java.awt.Color(255, 255, 255));
        dal_Container4.setPreferredSize(new java.awt.Dimension(400, 600));
        dal_Container4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel34.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container4.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        txtTenTD1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtTenTD1.setForeground(new java.awt.Color(255, 102, 51));
        txtTenTD1.setText("MNB");
        txtTenTD1.setBorder(null);
        txtTenTD1.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container4.add(txtTenTD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        jLabel35.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(51, 51, 51));
        jLabel35.setText("Tên tiêu đề:");
        dal_Container4.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 100, 20));

        jPanel35.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container4.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        txtNhaSX1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtNhaSX1.setForeground(new java.awt.Color(255, 102, 51));
        txtNhaSX1.setText("MNB");
        txtNhaSX1.setBorder(null);
        txtNhaSX1.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container4.add(txtNhaSX1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        jLabel36.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(51, 51, 51));
        jLabel36.setText("Nhà sản xuất:");
        dal_Container4.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 100, 20));

        jPanel36.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container4.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        txtNgaySX1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtNgaySX1.setForeground(new java.awt.Color(255, 102, 51));
        txtNgaySX1.setText("22/11/1999");
        txtNgaySX1.setBorder(null);
        txtNgaySX1.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container4.add(txtNgaySX1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        jLabel37.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(51, 51, 51));
        jLabel37.setText("Ngày sản xuất:");
        dal_Container4.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 100, 20));

        jPanel37.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container4.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));

        txtHinhAnh1.setEditable(false);
        txtHinhAnh1.setBackground(new java.awt.Color(255, 255, 255));
        txtHinhAnh1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtHinhAnh1.setForeground(new java.awt.Color(255, 102, 51));
        txtHinhAnh1.setText("doreamon.jpg");
        txtHinhAnh1.setBorder(null);
        txtHinhAnh1.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container4.add(txtHinhAnh1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 240, -1));

        jLabel38.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(51, 51, 51));
        jLabel38.setText("Hình ảnh:");
        dal_Container4.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 100, 20));

        jPanel38.setBackground(new java.awt.Color(255, 102, 51));

        btnHinhAnh1.setContentAreaFilled(false);
        btnHinhAnh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinhAnh1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnHinhAnh1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnHinhAnh1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dal_Container4.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 400, 50, 40));

        jPanel39.setBackground(new java.awt.Color(255, 102, 51));

        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Thêm");
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dal_Container4.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 460, 100, 40));

        jPanel40.setBackground(new java.awt.Color(255, 102, 51));
        jPanel40.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel40MouseDragged(evt);
            }
        });
        jPanel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel40MousePressed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Thêm thông tin tiêu đề");
        jLabel39.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel39MouseDragged(evt);
            }
        });
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel39MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        dal_Container4.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 50));

        jLabel40.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(51, 51, 51));
        jLabel40.setText("Nhà sản xuất:");
        dal_Container4.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 100, 20));

        cbbDVD1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phim", "Game" }));
        dal_Container4.add(cbbDVD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 130, 30));

        javax.swing.GroupLayout dal_ThemTDLayout = new javax.swing.GroupLayout(dal_ThemTD.getContentPane());
        dal_ThemTD.getContentPane().setLayout(dal_ThemTDLayout);
        dal_ThemTDLayout.setHorizontalGroup(
            dal_ThemTDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dal_ThemTDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dal_Container4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dal_ThemTDLayout.setVerticalGroup(
            dal_ThemTDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dal_ThemTDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dal_Container4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        them_DVD.setResizable(false);
        them_DVD.setSize(new java.awt.Dimension(400, 600));

        dal_Container5.setBackground(new java.awt.Color(255, 255, 255));
        dal_Container5.setForeground(new java.awt.Color(255, 255, 255));
        dal_Container5.setPreferredSize(new java.awt.Dimension(400, 600));
        dal_Container5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel41.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container5.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel41.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(51, 51, 51));
        jLabel41.setText("Tên Tiêu Đề:");
        dal_Container5.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 100, 20));

        jPanel42.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container5.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        txtMaDVD.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtMaDVD.setForeground(new java.awt.Color(255, 102, 51));
        txtMaDVD.setText("MNB");
        txtMaDVD.setBorder(null);
        txtMaDVD.setPreferredSize(new java.awt.Dimension(300, 40));
        dal_Container5.add(txtMaDVD, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        jLabel42.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(51, 51, 51));
        jLabel42.setText("Mã DVD:");
        dal_Container5.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 100, 20));

        jPanel43.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container5.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        jPanel44.setPreferredSize(new java.awt.Dimension(300, 2));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        dal_Container5.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));

        jPanel46.setBackground(new java.awt.Color(255, 102, 51));

        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Thêm");
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dal_Container5.add(jPanel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 460, 100, 40));

        jPanel47.setBackground(new java.awt.Color(255, 102, 51));
        jPanel47.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel47MouseDragged(evt);
            }
        });
        jPanel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel47MousePressed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Thêm DVD");
        jLabel45.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel45MouseDragged(evt);
            }
        });
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel45MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        dal_Container5.add(jPanel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 50));

        cbTenTieuDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTenTieuDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTenTieuDeActionPerformed(evt);
            }
        });
        dal_Container5.add(cbTenTieuDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 300, 40));

        javax.swing.GroupLayout them_DVDLayout = new javax.swing.GroupLayout(them_DVD.getContentPane());
        them_DVD.getContentPane().setLayout(them_DVDLayout);
        them_DVDLayout.setHorizontalGroup(
            them_DVDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(them_DVDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dal_Container5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        them_DVDLayout.setVerticalGroup(
            them_DVDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(them_DVDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dal_Container5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        container.setBackground(new java.awt.Color(237, 241, 242));
        container.setPreferredSize(new java.awt.Dimension(1000, 600));
        container.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlMenu.setBackground(new java.awt.Color(255, 255, 255));
        pnlMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlMenu.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, -1));

        pnl_btn_QuanLyChoThue.setBackground(new java.awt.Color(255, 255, 255));
        pnl_btn_QuanLyChoThue.setPreferredSize(new java.awt.Dimension(190, 40));
        pnl_btn_QuanLyChoThue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnQuanLyChoThue.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnQuanLyChoThue.setForeground(new java.awt.Color(255, 102, 51));
        btnQuanLyChoThue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/test.png"))); // NOI18N
        btnQuanLyChoThue.setText("Quản lý cho thuê");
        btnQuanLyChoThue.setBorder(null);
        btnQuanLyChoThue.setContentAreaFilled(false);
        btnQuanLyChoThue.setPreferredSize(new java.awt.Dimension(190, 39));
        btnQuanLyChoThue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyChoThueActionPerformed(evt);
            }
        });
        pnl_btn_QuanLyChoThue.add(btnQuanLyChoThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnl_hl1.setBackground(new java.awt.Color(255, 102, 51));
        pnl_hl1.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout pnl_hl1Layout = new javax.swing.GroupLayout(pnl_hl1);
        pnl_hl1.setLayout(pnl_hl1Layout);
        pnl_hl1Layout.setHorizontalGroup(
            pnl_hl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        pnl_hl1Layout.setVerticalGroup(
            pnl_hl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyChoThue.add(pnl_hl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 0, -1, -1));

        pnl_sp1.setBackground(new java.awt.Color(204, 204, 204));
        pnl_sp1.setPreferredSize(new java.awt.Dimension(100, 1));

        javax.swing.GroupLayout pnl_sp1Layout = new javax.swing.GroupLayout(pnl_sp1);
        pnl_sp1.setLayout(pnl_sp1Layout);
        pnl_sp1Layout.setHorizontalGroup(
            pnl_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pnl_sp1Layout.setVerticalGroup(
            pnl_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyChoThue.add(pnl_sp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 39, -1, -1));

        pnlMenu.add(pnl_btn_QuanLyChoThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 190, -1));

        pnl_btn_QuanLyKhachHang.setBackground(new java.awt.Color(255, 255, 255));
        pnl_btn_QuanLyKhachHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnQuanLyKhachHang.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnQuanLyKhachHang.setText("Quản lý khách hàng");
        btnQuanLyKhachHang.setBorder(null);
        btnQuanLyKhachHang.setContentAreaFilled(false);
        btnQuanLyKhachHang.setPreferredSize(new java.awt.Dimension(190, 39));
        btnQuanLyKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyKhachHangActionPerformed(evt);
            }
        });
        pnl_btn_QuanLyKhachHang.add(btnQuanLyKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnl_hl2.setBackground(new java.awt.Color(255, 255, 255));
        pnl_hl2.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout pnl_hl2Layout = new javax.swing.GroupLayout(pnl_hl2);
        pnl_hl2.setLayout(pnl_hl2Layout);
        pnl_hl2Layout.setHorizontalGroup(
            pnl_hl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        pnl_hl2Layout.setVerticalGroup(
            pnl_hl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyKhachHang.add(pnl_hl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 0, -1, -1));

        pnl_sp2.setBackground(new java.awt.Color(204, 204, 204));
        pnl_sp2.setPreferredSize(new java.awt.Dimension(100, 1));

        javax.swing.GroupLayout pnl_sp2Layout = new javax.swing.GroupLayout(pnl_sp2);
        pnl_sp2.setLayout(pnl_sp2Layout);
        pnl_sp2Layout.setHorizontalGroup(
            pnl_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pnl_sp2Layout.setVerticalGroup(
            pnl_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyKhachHang.add(pnl_sp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 39, -1, -1));

        pnlMenu.add(pnl_btn_QuanLyKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 190, -1));

        pnl_btn_QuanLyTraDia.setBackground(new java.awt.Color(255, 255, 255));
        pnl_btn_QuanLyTraDia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnQuanLyTraDia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnQuanLyTraDia.setText("Quản lý trả đĩa");
        btnQuanLyTraDia.setBorder(null);
        btnQuanLyTraDia.setContentAreaFilled(false);
        btnQuanLyTraDia.setPreferredSize(new java.awt.Dimension(190, 39));
        btnQuanLyTraDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyTraDiaActionPerformed(evt);
            }
        });
        pnl_btn_QuanLyTraDia.add(btnQuanLyTraDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnl_hl3.setBackground(new java.awt.Color(255, 255, 255));
        pnl_hl3.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout pnl_hl3Layout = new javax.swing.GroupLayout(pnl_hl3);
        pnl_hl3.setLayout(pnl_hl3Layout);
        pnl_hl3Layout.setHorizontalGroup(
            pnl_hl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        pnl_hl3Layout.setVerticalGroup(
            pnl_hl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyTraDia.add(pnl_hl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 0, -1, -1));

        pnl_sp3.setBackground(new java.awt.Color(204, 204, 204));
        pnl_sp3.setPreferredSize(new java.awt.Dimension(100, 1));

        javax.swing.GroupLayout pnl_sp3Layout = new javax.swing.GroupLayout(pnl_sp3);
        pnl_sp3.setLayout(pnl_sp3Layout);
        pnl_sp3Layout.setHorizontalGroup(
            pnl_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pnl_sp3Layout.setVerticalGroup(
            pnl_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyTraDia.add(pnl_sp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 39, -1, -1));

        pnlMenu.add(pnl_btn_QuanLyTraDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 190, -1));

        pnl_btn_QuanLyDatTruoc.setBackground(new java.awt.Color(255, 255, 255));
        pnl_btn_QuanLyDatTruoc.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnQuanLyDatTruoc.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnQuanLyDatTruoc.setText("Quản lý đặt trước");
        btnQuanLyDatTruoc.setBorder(null);
        btnQuanLyDatTruoc.setContentAreaFilled(false);
        btnQuanLyDatTruoc.setPreferredSize(new java.awt.Dimension(190, 39));
        btnQuanLyDatTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyDatTruocActionPerformed(evt);
            }
        });
        pnl_btn_QuanLyDatTruoc.add(btnQuanLyDatTruoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnl_hl4.setBackground(new java.awt.Color(255, 255, 255));
        pnl_hl4.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout pnl_hl4Layout = new javax.swing.GroupLayout(pnl_hl4);
        pnl_hl4.setLayout(pnl_hl4Layout);
        pnl_hl4Layout.setHorizontalGroup(
            pnl_hl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        pnl_hl4Layout.setVerticalGroup(
            pnl_hl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyDatTruoc.add(pnl_hl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 0, -1, -1));

        pnl_sp4.setBackground(new java.awt.Color(204, 204, 204));
        pnl_sp4.setPreferredSize(new java.awt.Dimension(100, 1));

        javax.swing.GroupLayout pnl_sp4Layout = new javax.swing.GroupLayout(pnl_sp4);
        pnl_sp4.setLayout(pnl_sp4Layout);
        pnl_sp4Layout.setHorizontalGroup(
            pnl_sp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pnl_sp4Layout.setVerticalGroup(
            pnl_sp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyDatTruoc.add(pnl_sp4, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 39, -1, -1));

        pnlMenu.add(pnl_btn_QuanLyDatTruoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 190, -1));

        pnl_btn_QuanLyChiPhi.setBackground(new java.awt.Color(255, 255, 255));
        pnl_btn_QuanLyChiPhi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnQuanLyChiPhi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnQuanLyChiPhi.setText("Quản lý chi phí");
        btnQuanLyChiPhi.setBorder(null);
        btnQuanLyChiPhi.setContentAreaFilled(false);
        btnQuanLyChiPhi.setPreferredSize(new java.awt.Dimension(190, 39));
        btnQuanLyChiPhi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyChiPhiActionPerformed(evt);
            }
        });
        pnl_btn_QuanLyChiPhi.add(btnQuanLyChiPhi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnl_hl5.setBackground(new java.awt.Color(255, 255, 255));
        pnl_hl5.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout pnl_hl5Layout = new javax.swing.GroupLayout(pnl_hl5);
        pnl_hl5.setLayout(pnl_hl5Layout);
        pnl_hl5Layout.setHorizontalGroup(
            pnl_hl5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        pnl_hl5Layout.setVerticalGroup(
            pnl_hl5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyChiPhi.add(pnl_hl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 0, -1, -1));

        pnl_sp5.setBackground(new java.awt.Color(204, 204, 204));
        pnl_sp5.setPreferredSize(new java.awt.Dimension(100, 1));

        javax.swing.GroupLayout pnl_sp5Layout = new javax.swing.GroupLayout(pnl_sp5);
        pnl_sp5.setLayout(pnl_sp5Layout);
        pnl_sp5Layout.setHorizontalGroup(
            pnl_sp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pnl_sp5Layout.setVerticalGroup(
            pnl_sp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyChiPhi.add(pnl_sp5, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 39, -1, -1));

        pnlMenu.add(pnl_btn_QuanLyChiPhi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 190, -1));

        pnl_btn_QuanLyTieuDe.setBackground(new java.awt.Color(255, 255, 255));
        pnl_btn_QuanLyTieuDe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnQuanLyTieuDe.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnQuanLyTieuDe.setText("Quản lý tiêu đề");
        btnQuanLyTieuDe.setBorder(null);
        btnQuanLyTieuDe.setContentAreaFilled(false);
        btnQuanLyTieuDe.setPreferredSize(new java.awt.Dimension(190, 39));
        btnQuanLyTieuDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyTieuDeActionPerformed(evt);
            }
        });
        pnl_btn_QuanLyTieuDe.add(btnQuanLyTieuDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnl_hl6.setBackground(new java.awt.Color(255, 255, 255));
        pnl_hl6.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout pnl_hl6Layout = new javax.swing.GroupLayout(pnl_hl6);
        pnl_hl6.setLayout(pnl_hl6Layout);
        pnl_hl6Layout.setHorizontalGroup(
            pnl_hl6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        pnl_hl6Layout.setVerticalGroup(
            pnl_hl6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyTieuDe.add(pnl_hl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 0, -1, -1));

        pnl_sp6.setBackground(new java.awt.Color(204, 204, 204));
        pnl_sp6.setPreferredSize(new java.awt.Dimension(100, 1));

        javax.swing.GroupLayout pnl_sp6Layout = new javax.swing.GroupLayout(pnl_sp6);
        pnl_sp6.setLayout(pnl_sp6Layout);
        pnl_sp6Layout.setHorizontalGroup(
            pnl_sp6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pnl_sp6Layout.setVerticalGroup(
            pnl_sp6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyTieuDe.add(pnl_sp6, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 39, -1, -1));

        pnlMenu.add(pnl_btn_QuanLyTieuDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 190, -1));

        pnl_btn_QuanLyKhoDia.setBackground(new java.awt.Color(255, 255, 255));
        pnl_btn_QuanLyKhoDia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnQuanLyKhoDia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnQuanLyKhoDia.setText("Quản lý kho đĩa");
        btnQuanLyKhoDia.setBorder(null);
        btnQuanLyKhoDia.setContentAreaFilled(false);
        btnQuanLyKhoDia.setPreferredSize(new java.awt.Dimension(190, 39));
        btnQuanLyKhoDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyKhoDiaActionPerformed(evt);
            }
        });
        pnl_btn_QuanLyKhoDia.add(btnQuanLyKhoDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnl_hl7.setBackground(new java.awt.Color(255, 255, 255));
        pnl_hl7.setPreferredSize(new java.awt.Dimension(4, 40));

        javax.swing.GroupLayout pnl_hl7Layout = new javax.swing.GroupLayout(pnl_hl7);
        pnl_hl7.setLayout(pnl_hl7Layout);
        pnl_hl7Layout.setHorizontalGroup(
            pnl_hl7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        pnl_hl7Layout.setVerticalGroup(
            pnl_hl7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyKhoDia.add(pnl_hl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 0, -1, -1));

        pnl_sp7.setBackground(new java.awt.Color(204, 204, 204));
        pnl_sp7.setPreferredSize(new java.awt.Dimension(100, 1));

        javax.swing.GroupLayout pnl_sp7Layout = new javax.swing.GroupLayout(pnl_sp7);
        pnl_sp7.setLayout(pnl_sp7Layout);
        pnl_sp7Layout.setHorizontalGroup(
            pnl_sp7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pnl_sp7Layout.setVerticalGroup(
            pnl_sp7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        pnl_btn_QuanLyKhoDia.add(pnl_sp7, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 39, -1, -1));

        pnlMenu.add(pnl_btn_QuanLyKhoDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 190, -1));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(251, 125, 20));
        jLabel1.setText("Quản lý");
        pnlMenu.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 130, -1));

        DangNhap.setText("Đăng Nhập");
        DangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DangNhapActionPerformed(evt);
            }
        });
        pnlMenu.add(DangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 550, -1, -1));

        container.add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 600));

        sdMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/img/shadowMenu_1.png"))); // NOI18N
        sdMenu.setPreferredSize(new java.awt.Dimension(100, 570));
        container.add(sdMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 120, 600));

        codated.setBackground(new java.awt.Color(255, 255, 255));
        codated.setForeground(new java.awt.Color(204, 204, 204));
        codated.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                codatedMouseDragged(evt);
            }
        });
        codated.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                codatedMousePressed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/img/cancel.png"))); // NOI18N
        btnClose.setContentAreaFilled(false);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lbTitle.setBackground(new java.awt.Color(1, 206, 140));
        lbTitle.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(0, 102, 204));
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Quản lý cho thuê DVD");

        btnClose1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/img/forbidden.png"))); // NOI18N
        btnClose1.setContentAreaFilled(false);
        btnClose1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClose1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout codatedLayout = new javax.swing.GroupLayout(codated);
        codated.setLayout(codatedLayout);
        codatedLayout.setHorizontalGroup(
            codatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, codatedLayout.createSequentialGroup()
                .addContainerGap(449, Short.MAX_VALUE)
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(312, 312, 312)
                .addComponent(btnClose1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        codatedLayout.setVerticalGroup(
            codatedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnClose1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        container.add(codated, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 40));

        container_Content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlQuanLyChoThue.setBackground(new java.awt.Color(241, 245, 246));
        pnlQuanLyChoThue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_TongTien.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lb_TongTien.setForeground(new java.awt.Color(204, 51, 0));
        pnlQuanLyChoThue.add(lb_TongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 520, 130, 30));

        txtSearchIDDVD.setBackground(new java.awt.Color(241, 245, 246));
        txtSearchIDDVD.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSearchIDDVD.setForeground(new java.awt.Color(102, 102, 102));
        txtSearchIDDVD.setBorder(null);
        txtSearchIDDVD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchIDDVDKeyReleased(evt);
            }
        });
        pnlQuanLyChoThue.add(txtSearchIDDVD, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 222, 40));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setPreferredSize(new java.awt.Dimension(220, 2));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        pnlQuanLyChoThue.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        btnSearch.setBackground(new java.awt.Color(51, 51, 51));
        btnSearch.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(51, 51, 51));
        btnSearch.setText("Huỷ");
        btnSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSearch.setContentAreaFilled(false);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        pnlQuanLyChoThue.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 520, 120, 30));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Nhập ID DVD:");
        pnlQuanLyChoThue.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 140, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Nhập ID khách hàng:");
        pnlQuanLyChoThue.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 140, -1));

        txtSearchKH_Thue.setBackground(new java.awt.Color(241, 245, 246));
        txtSearchKH_Thue.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSearchKH_Thue.setForeground(new java.awt.Color(102, 102, 102));
        txtSearchKH_Thue.setBorder(null);
        txtSearchKH_Thue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKH_ThueKeyReleased(evt);
            }
        });
        pnlQuanLyChoThue.add(txtSearchKH_Thue, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 222, 40));

        btn_CheckKH.setBackground(new java.awt.Color(51, 51, 51));
        btn_CheckKH.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_CheckKH.setForeground(new java.awt.Color(51, 51, 51));
        btn_CheckKH.setText("Kiểm tra");
        btn_CheckKH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_CheckKH.setContentAreaFilled(false);
        btn_CheckKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CheckKHActionPerformed(evt);
            }
        });
        pnlQuanLyChoThue.add(btn_CheckKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 80, 40));

        jPanel22.setBackground(new java.awt.Color(204, 204, 204));
        jPanel22.setPreferredSize(new java.awt.Dimension(220, 2));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        pnlQuanLyChoThue.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Tông tiền:");
        pnlQuanLyChoThue.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, 90, 30));

        btn_CheckDVD.setBackground(new java.awt.Color(51, 51, 51));
        btn_CheckDVD.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_CheckDVD.setForeground(new java.awt.Color(51, 51, 51));
        btn_CheckDVD.setText("Add");
        btn_CheckDVD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_CheckDVD.setContentAreaFilled(false);
        btn_CheckDVD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CheckDVDActionPerformed(evt);
            }
        });
        pnlQuanLyChoThue.add(btn_CheckDVD, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 80, 40));

        btnSearch4.setBackground(new java.awt.Color(51, 51, 51));
        btnSearch4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnSearch4.setForeground(new java.awt.Color(51, 51, 51));
        btnSearch4.setText("Thanh toán");
        btnSearch4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSearch4.setContentAreaFilled(false);
        btnSearch4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch4ActionPerformed(evt);
            }
        });
        pnlQuanLyChoThue.add(btnSearch4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 520, 120, 30));

        jScrollPane8.setBackground(new java.awt.Color(241, 245, 246));
        jScrollPane8.setBorder(null);
        jScrollPane8.setPreferredSize(new java.awt.Dimension(720, 450));

        list_DVDtemp.setBackground(new java.awt.Color(241, 245, 246));
        list_DVDtemp.setComponentPopupMenu(MoOption_DVDThue);
        list_DVDtemp.setPreferredSize(new java.awt.Dimension(721, 450));
        list_DVDtemp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                list_DVDtempMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(list_DVDtemp);

        pnlQuanLyChoThue.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 744, 330));

        lb_infoTenKH.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lb_infoTenKH.setForeground(new java.awt.Color(51, 51, 51));
        pnlQuanLyChoThue.add(lb_infoTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, 220, -1));

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("Họ & tên:");
        pnlQuanLyChoThue.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, 80, -1));

        btn_CheckKH1.setBackground(new java.awt.Color(51, 51, 51));
        btn_CheckKH1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_CheckKH1.setForeground(new java.awt.Color(51, 51, 51));
        btn_CheckKH1.setText("Xem phí trễ");
        btn_CheckKH1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_CheckKH1.setContentAreaFilled(false);
        btn_CheckKH1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CheckKH1ActionPerformed(evt);
            }
        });
        pnlQuanLyChoThue.add(btn_CheckKH1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 40, 80, 30));

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("Mã KH:");
        pnlQuanLyChoThue.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 80, -1));

        lb_infoMaKH.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lb_infoMaKH.setForeground(new java.awt.Color(51, 51, 51));
        pnlQuanLyChoThue.add(lb_infoMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 220, -1));

        txtSoNgayMuon.setBackground(new java.awt.Color(241, 245, 246));
        txtSoNgayMuon.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSoNgayMuon.setForeground(new java.awt.Color(102, 102, 102));
        txtSoNgayMuon.setBorder(null);
        txtSoNgayMuon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoNgayMuonKeyReleased(evt);
            }
        });
        pnlQuanLyChoThue.add(txtSoNgayMuon, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 110, 222, 40));

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setText("Nhập số ngày mượn:");
        pnlQuanLyChoThue.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, 140, -1));

        jPanel23.setBackground(new java.awt.Color(204, 204, 204));
        jPanel23.setPreferredSize(new java.awt.Dimension(220, 2));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        pnlQuanLyChoThue.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, -1, -1));

        container_Content.add(pnlQuanLyChoThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        pnlQuanLyKhachHang.setBackground(new java.awt.Color(241, 245, 246));
        pnlQuanLyKhachHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSearch_KH.setBorder(null);
        btnSearch_KH.setContentAreaFilled(false);
        pnlQuanLyKhachHang.add(btnSearch_KH, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 30, 30));

        txtSearch_KH.setBackground(new java.awt.Color(241, 245, 246));
        txtSearch_KH.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSearch_KH.setForeground(new java.awt.Color(102, 102, 102));
        txtSearch_KH.setBorder(null);
        txtSearch_KH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearch_KHActionPerformed(evt);
            }
        });
        txtSearch_KH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch_KHKeyReleased(evt);
            }
        });
        pnlQuanLyKhachHang.add(txtSearch_KH, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 222, 30));

        jPanel12.setPreferredSize(new java.awt.Dimension(220, 2));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        pnlQuanLyKhachHang.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, -1, -1));

        jScrollPane3.setBackground(new java.awt.Color(241, 245, 246));
        jScrollPane3.setBorder(null);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(720, 450));

        list_KhachHang.setBackground(new java.awt.Color(241, 245, 246));
        list_KhachHang.setComponentPopupMenu(MoOption_KhachHang);
        list_KhachHang.setPreferredSize(new java.awt.Dimension(721, 450));
        list_KhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                list_KhachHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(list_KhachHang);

        pnlQuanLyKhachHang.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 47, 744, 436));

        jLabel7.setBackground(new java.awt.Color(51, 51, 51));
        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Danh sách khách hàng");
        pnlQuanLyKhachHang.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 12, 240, 33));

        btnThemKH.setText("Thêm");
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });
        pnlQuanLyKhachHang.add(btnThemKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 80, 30));

        container_Content.add(pnlQuanLyKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        pnlQuanLyTraDia.setBackground(new java.awt.Color(241, 245, 246));
        pnlQuanLyTraDia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane5.setBackground(new java.awt.Color(241, 245, 246));
        jScrollPane5.setBorder(null);
        jScrollPane5.setPreferredSize(new java.awt.Dimension(720, 450));

        list_TraDia.setBackground(new java.awt.Color(241, 245, 246));
        list_TraDia.setComponentPopupMenu(MoOption_KhachHang);
        list_TraDia.setPreferredSize(new java.awt.Dimension(721, 450));
        list_TraDia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                list_TraDiaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(list_TraDia);

        pnlQuanLyTraDia.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 47, 744, 436));

        jLabel8.setBackground(new java.awt.Color(51, 51, 51));
        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Danh sách trả đĩa");
        pnlQuanLyTraDia.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 12, 342, 33));

        jPanel18.setPreferredSize(new java.awt.Dimension(220, 2));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        pnlQuanLyTraDia.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, -1, -1));

        txtSearch_KH1.setBackground(new java.awt.Color(241, 245, 246));
        txtSearch_KH1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSearch_KH1.setForeground(new java.awt.Color(102, 102, 102));
        txtSearch_KH1.setBorder(null);
        txtSearch_KH1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch_KH1KeyReleased(evt);
            }
        });
        pnlQuanLyTraDia.add(txtSearch_KH1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 222, 30));

        btnSearch_KH2.setBorder(null);
        btnSearch_KH2.setContentAreaFilled(false);
        pnlQuanLyTraDia.add(btnSearch_KH2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 30, 30));

        container_Content.add(pnlQuanLyTraDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        pnlQuanLyDatTruoc.setBackground(new java.awt.Color(241, 245, 246));
        pnlQuanLyDatTruoc.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane6.setBackground(new java.awt.Color(241, 245, 246));
        jScrollPane6.setBorder(null);
        jScrollPane6.setPreferredSize(new java.awt.Dimension(720, 450));

        list_TraDia1.setBackground(new java.awt.Color(241, 245, 246));
        list_TraDia1.setComponentPopupMenu(MoOption_KhachHang);
        list_TraDia1.setPreferredSize(new java.awt.Dimension(721, 450));
        list_TraDia1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                list_TraDia1MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(list_TraDia1);

        pnlQuanLyDatTruoc.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 47, 744, 436));

        jLabel22.setBackground(new java.awt.Color(51, 51, 51));
        jLabel22.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Danh sách trả đĩa");
        pnlQuanLyDatTruoc.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 12, 342, 33));

        jPanel21.setPreferredSize(new java.awt.Dimension(220, 2));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        pnlQuanLyDatTruoc.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, -1, -1));

        txtSearch_KH2.setBackground(new java.awt.Color(241, 245, 246));
        txtSearch_KH2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSearch_KH2.setForeground(new java.awt.Color(102, 102, 102));
        txtSearch_KH2.setBorder(null);
        txtSearch_KH2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch_KH2KeyReleased(evt);
            }
        });
        pnlQuanLyDatTruoc.add(txtSearch_KH2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 222, 30));

        btnSearch_KH3.setBorder(null);
        btnSearch_KH3.setContentAreaFilled(false);
        pnlQuanLyDatTruoc.add(btnSearch_KH3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 30, 30));

        container_Content.add(pnlQuanLyDatTruoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        pnlQuanLyChiphi.setBackground(new java.awt.Color(241, 245, 246));

        jLabel6.setText("Chi phi");

        javax.swing.GroupLayout pnlQuanLyChiphiLayout = new javax.swing.GroupLayout(pnlQuanLyChiphi);
        pnlQuanLyChiphi.setLayout(pnlQuanLyChiphiLayout);
        pnlQuanLyChiphiLayout.setHorizontalGroup(
            pnlQuanLyChiphiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyChiphiLayout.createSequentialGroup()
                .addGap(364, 364, 364)
                .addComponent(jLabel6)
                .addContainerGap(414, Short.MAX_VALUE))
        );
        pnlQuanLyChiphiLayout.setVerticalGroup(
            pnlQuanLyChiphiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyChiphiLayout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(jLabel6)
                .addContainerGap(316, Short.MAX_VALUE))
        );

        container_Content.add(pnlQuanLyChiphi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        pnlQuanLyTieuDe.setBackground(new java.awt.Color(241, 245, 246));
        pnlQuanLyTieuDe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBackground(new java.awt.Color(241, 245, 246));
        jScrollPane2.setBorder(null);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(720, 450));

        list_TieuDe_Manager.setBackground(new java.awt.Color(241, 245, 246));
        list_TieuDe_Manager.setComponentPopupMenu(MoOption_TieuDe);
        list_TieuDe_Manager.setPreferredSize(new java.awt.Dimension(721, 450));
        jScrollPane2.setViewportView(list_TieuDe_Manager);

        pnlQuanLyTieuDe.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 47, 744, 436));

        jLabel15.setBackground(new java.awt.Color(51, 51, 51));
        jLabel15.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Danh sách tiêu đề");
        pnlQuanLyTieuDe.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 12, 200, 33));

        txtSearch1.setBackground(new java.awt.Color(241, 245, 246));
        txtSearch1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSearch1.setForeground(new java.awt.Color(102, 102, 102));
        txtSearch1.setBorder(null);
        txtSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch1KeyReleased(evt);
            }
        });
        pnlQuanLyTieuDe.add(txtSearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 222, 30));

        btnSearch1.setBorder(null);
        btnSearch1.setContentAreaFilled(false);
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });
        pnlQuanLyTieuDe.add(btnSearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 30, 30));

        jPanel11.setPreferredSize(new java.awt.Dimension(220, 2));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        pnlQuanLyTieuDe.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, -1, -1));

        btnThemTD.setText("Thêm");
        btnThemTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTDActionPerformed(evt);
            }
        });
        pnlQuanLyTieuDe.add(btnThemTD, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 100, 30));

        container_Content.add(pnlQuanLyTieuDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        pnlQuanLyKhoDia.setBackground(new java.awt.Color(241, 245, 246));
        pnlQuanLyKhoDia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setBackground(new java.awt.Color(241, 245, 246));
        jScrollPane4.setBorder(null);
        jScrollPane4.setPreferredSize(new java.awt.Dimension(720, 450));

        list_DVD.setBackground(new java.awt.Color(241, 245, 246));
        list_DVD.setComponentPopupMenu(MoOption_KhachHang);
        list_DVD.setPreferredSize(new java.awt.Dimension(721, 450));
        list_DVD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                list_DVDMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(list_DVD);

        pnlQuanLyKhoDia.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 47, 744, 436));

        jPanel17.setPreferredSize(new java.awt.Dimension(220, 2));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        pnlQuanLyKhoDia.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, -1, -1));

        jLabel20.setBackground(new java.awt.Color(51, 51, 51));
        jLabel20.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Kho đĩa");
        pnlQuanLyKhoDia.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 160, 33));

        txtSearch_DVD.setBackground(new java.awt.Color(241, 245, 246));
        txtSearch_DVD.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSearch_DVD.setForeground(new java.awt.Color(102, 102, 102));
        txtSearch_DVD.setBorder(null);
        txtSearch_DVD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch_DVDKeyReleased(evt);
            }
        });
        pnlQuanLyKhoDia.add(txtSearch_DVD, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 222, 30));

        btnSearch_KH1.setBorder(null);
        btnSearch_KH1.setContentAreaFilled(false);
        pnlQuanLyKhoDia.add(btnSearch_KH1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 30, 30));

        btnThemDVD.setText("Thêm");
        btnThemDVD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDVDActionPerformed(evt);
            }
        });
        pnlQuanLyKhoDia.add(btnThemDVD, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));

        container_Content.add(pnlQuanLyKhoDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 560));

        container.add(container_Content, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 810, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Giang - Move jFrame
    private void codatedMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_codatedMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - moveX, y - moveY);
    }//GEN-LAST:event_codatedMouseDragged
    //Giang - Move jFrame
    private void codatedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_codatedMousePressed
        moveX = evt.getX();
        moveY = evt.getY();
    }//GEN-LAST:event_codatedMousePressed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát không?") == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnQuanLyChoThueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyChoThueActionPerformed
        actionChangeHover(btnQuanLyChoThue);
    }//GEN-LAST:event_btnQuanLyChoThueActionPerformed

    private void btnQuanLyKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyKhachHangActionPerformed
        //Hover
        actionChangeHover(btnQuanLyKhachHang);
        //End Hover
    }//GEN-LAST:event_btnQuanLyKhachHangActionPerformed

    private void btnQuanLyTraDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyTraDiaActionPerformed
        //Hover
        actionChangeHover(btnQuanLyTraDia);
        //End Hover
    }//GEN-LAST:event_btnQuanLyTraDiaActionPerformed

    private void btnQuanLyDatTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyDatTruocActionPerformed
        //Hover
        actionChangeHover(btnQuanLyDatTruoc);
        //End Hover
    }//GEN-LAST:event_btnQuanLyDatTruocActionPerformed

    private void btnQuanLyChiPhiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyChiPhiActionPerformed
        //Hover
        actionChangeHover(btnQuanLyChiPhi);
        //End Hover
    }//GEN-LAST:event_btnQuanLyChiPhiActionPerformed

    private void btnQuanLyTieuDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyTieuDeActionPerformed
        //Hover
        actionChangeHover(btnQuanLyTieuDe);
        //End Hover
    }//GEN-LAST:event_btnQuanLyTieuDeActionPerformed

    private void btnQuanLyKhoDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyKhoDiaActionPerformed
        //Hover
        actionChangeHover(btnQuanLyKhoDia);
        //End Hover
    }//GEN-LAST:event_btnQuanLyKhoDiaActionPerformed

    private void btnClose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose1ActionPerformed
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_btnClose1ActionPerformed

    private void popChangeStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popChangeStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_popChangeStatusActionPerformed

    private void popChinhSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popChinhSuaActionPerformed
        int[] selectedIx = list_TieuDe_Manager.getSelectedIndices();
        int count = selectedIx.length;
        if (count == 1) {
            int index = list_TieuDe_Manager.getSelectedIndex();
            dal_ChinhSuaTieuDe.setVisible(true);
            list_TieuDe_Manager.getSelectedIndex();
            txtMaTD.setText(modelTieuDe.get(index).getTdMa());
            txtTenTD.setText(modelTieuDe.get(index).getTdTenTD());
            txtNgaySX.setText(ft_ngay.format(modelTieuDe.get(index).getTdNgay()));
            txtNhaSX.setText(modelTieuDe.get(index).getTdNhaSX());
        } else if (count > 1) {
            JOptionPane.showMessageDialog(null, "Chức năng chỉ thực hiện cho 1 tiêu đề!");
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn tiêu đề!");
        }
    }//GEN-LAST:event_popChinhSuaActionPerformed

    private void txtSearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch1KeyReleased
        String tenTD = txtSearch1.getText().trim();
        if (tenTD.equals("")) {
            showTitle();
        }
        listTD = tddao.findTDByTen(tenTD);
        modelTieuDe.removeAllElements();

        for (int i = 0; i < listTD.size(); i++) {
            modelTieuDe.addElement(listTD.get(i));
        }
        list_TieuDe_Manager.setModel(modelTieuDe);
        list_TieuDe_Manager.setCellRenderer(new formTieuDe());
    }//GEN-LAST:event_txtSearch1KeyReleased

    private void txtSearchIDDVDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchIDDVDKeyReleased

    }//GEN-LAST:event_txtSearchIDDVDKeyReleased

    private void txtSearch_KHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_KHKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearch_KHKeyReleased

    private void list_KhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_KhachHangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_list_KhachHangMouseClicked
    //Thịnh-Chỉnh sửa thông tin khách hàng
    private void popChinhSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popChinhSua1ActionPerformed
        int[] selectedIx = list_KhachHang.getSelectedIndices();
        int count = selectedIx.length;
        if (count == 1) {
            int index = list_KhachHang.getSelectedIndex();
            dal_ChinhSuaKhachHang.setVisible(true);
            list_KhachHang.getSelectedIndex();
            txtMaKH.setText(modelKhachHang.get(index).getKhMa());
            txtTenKH.setText(modelKhachHang.get(index).getKhHoVaTen());
            txtDiaChi.setText(modelKhachHang.get(index).getKhDiaChi());
            txtSDT.setText(modelKhachHang.get(index).getKhSDT());
        } else if (count > 1) {
            JOptionPane.showMessageDialog(null, "Chức năng chỉ thự hiện cho 1 đối tượng");
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn đối tượng cần sửa");
        }
    }//GEN-LAST:event_popChinhSua1ActionPerformed
    //Thịnh - Thay đổi tình trạng
    private void popChangeStatus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popChangeStatus1ActionPerformed
        int[] selectedIx = list_KhachHang.getSelectedIndices();
        int count = selectedIx.length;
        if (count == 1) {
            int index = list_KhachHang.getSelectedIndex();
            khdao.changeTT(modelKhachHang.get(index).getKhMa(), modelKhachHang.get(index).getKhTinhTrang());
            showKhachHang();
        } else if (count > 1) {
            JOptionPane.showMessageDialog(null, "Chức năng chỉ thự hiện cho 1 đối tượng");
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn đối tượng cần sửa");
        }
    }//GEN-LAST:event_popChangeStatus1ActionPerformed

    private void jLabel21MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21MouseDragged

    private void jLabel21MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel21MousePressed

    private void jPanel20MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel20MouseDragged

    private void jPanel20MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel20MousePressed

    private void list_DVDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_DVDMouseClicked

    }//GEN-LAST:event_list_DVDMouseClicked

    private void txtSearch_DVDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_DVDKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearch_DVDKeyReleased

    private void list_TraDiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_TraDiaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_list_TraDiaMouseClicked

    private void txtSearch_KH1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_KH1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearch_KH1KeyReleased

    private void list_TraDia1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_TraDia1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_list_TraDia1MouseClicked

    private void txtSearch_KH2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_KH2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearch_KH2KeyReleased

    private void txtSearchKH_ThueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKH_ThueKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKH_ThueKeyReleased

    private void list_DVDtempMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_DVDtempMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_list_DVDtempMouseClicked

    private void btn_CheckKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CheckKHActionPerformed
        boolean kq = false;
        String maKH = txtSearchKH_Thue.getText();
        for (int i = 0; i < listKH.size(); i++) {
            if (listKH.get(i).getKhMa().equalsIgnoreCase(maKH)) {
                kq = true;
                lb_infoTenKH.setText(listKH.get(i).getKhHoVaTen());
                lb_infoMaKH.setText(listKH.get(i).getKhMa());
                showTienPhat(txtSearchKH_Thue.getText());
                modelDVDThue.removeAllElements();
                break;
            }
        }
        if (!kq) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng!");
            lb_infoTenKH.setText("");
        } else {
            if (getList_TienPhat_formMaKH(maKH).size() != 0) {
                if (JOptionPane.showConfirmDialog(null, "Khách hàng có tiền phạt trể hạn. Thanh toán ?") == JOptionPane.YES_OPTION) {
                    dal_PhiTre.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_btn_CheckKHActionPerformed

    private void btn_CheckKH1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CheckKH1ActionPerformed
        dal_PhiTre.setVisible(true);
    }//GEN-LAST:event_btn_CheckKH1ActionPerformed

    private void btn_CheckKH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CheckKH2ActionPerformed
        int[] selectedIx = list_PhiTreHanThue.getSelectedIndices();
        double count = 0;
        for (int i = 0; i < selectedIx.length; i++) {
            count += list_PhiTreHanThue.getModel().getElementAt(selectedIx[i]).getPtMa().getPtTienPhat();
        }
        tinhTongTien();
        dal_PhiTre.setVisible(false);
    }//GEN-LAST:event_btn_CheckKH2ActionPerformed

    private void btn_CheckDVDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CheckDVDActionPerformed
        String idDVD = txtSearchIDDVD.getText();
        String error = null;
        ArrayList<Dvd> listDVD_jList = new ArrayList<>();
        for (int i = 0; i < list_DVDtemp.getModel().getSize(); i++) {
            listDVD_jList.add(list_DVDtemp.getModel().getElementAt(i));
        }
        for (int i = 0; i < listDVD.size(); i++) {
            if (!idDVD.equalsIgnoreCase(listDVD.get(i).getDvdMa())) {
                error = "DVD không tồn tại!";
            } else {
                if (listDVD.get(i).getDvdTrangThai()!= 1) {
                    error = "DVD không ở trạng thái cho thuê!";
                    break;
                } else {
                    if (!checkTrungListDVD(idDVD, listDVD_jList)) {
                        error = "DVD đã được thêm vào!";
                        break;
                    } else {
                        modelDVDThue.addElement(listDVD.get(i));
                        list_DVDtemp.setModel(modelDVDThue);
                        list_DVDtemp.setCellRenderer(new formDVD());
                        error = null;
                        break;
                    }
                }
            }
        }
        if (error != null) {
            JOptionPane.showMessageDialog(null, error);
        }
        tinhTongTien();
    }//GEN-LAST:event_btn_CheckDVDActionPerformed

    private void popLoaiBoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popLoaiBoActionPerformed
        int[] selectedIx = list_DVDtemp.getSelectedIndices();
        for (int i = selectedIx.length - 1; i > -1; i--) {
            modelDVDThue.removeElementAt(selectedIx[i]);
        }
        tinhTongTien();
    }//GEN-LAST:event_popLoaiBoActionPerformed

    private void dal_PhiTreWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dal_PhiTreWindowClosed

    }//GEN-LAST:event_dal_PhiTreWindowClosed

    private void dal_PhiTreWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dal_PhiTreWindowClosing
        tinhTongTien();
    }//GEN-LAST:event_dal_PhiTreWindowClosing

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        deleteValueThue();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSearch4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch4ActionPerformed
        String regex = "\\d*";
        if(lb_infoTenKH.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Chưa nhập khách hàng!");
            txtSearchKH_Thue.isFocusable();
        }else if(list_DVDtemp.getModel().getSize() == 0){
            JOptionPane.showMessageDialog(null, "Chưa chọn DVD!");
        }else if(txtSoNgayMuon.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Chưa nhập số ngày thuê!");
            txtSoNgayMuon.isFocusable();
        }else if(!txtSoNgayMuon.getText().matches(regex)){
            JOptionPane.showMessageDialog(null, "Số ngày thuê là số!");
        }else if(Integer.parseInt(txtSoNgayMuon.getText()) < 3 || Integer.parseInt(txtSoNgayMuon.getText()) > 30  ){
            JOptionPane.showMessageDialog(null, "Số ngày thuê là từ 3 - 30 ngày!");
        }else {
            int maHD = listHoaDon.size() + 1;
            int maCTHD = listCTHD.size() + 1;
            Calendar  c = new GregorianCalendar();
            c.add(Calendar.DATE, Integer.parseInt(txtSoNgayMuon.getText()));
            Date d=c.getTime();
            HoaDon hd = new HoaDon("HD" + maHD, new KhachHangDAO().findKHByMa(lb_infoMaKH.getText()), modelCTHD.getSize(), new Date(), d, true);
            new HoaDonDAO().createHoaDon(hd);
            for (int i = 0; i < list_DVDtemp.getModel().getSize(); i++) {
                new cthdDAO().createCTHD(new ChiTietHoaDon("CTHD"+maCTHD,hd, new dvdDAO().findDVDbyMa(list_DVDtemp.getModel().getElementAt(i).getDvdMa()), new TienPhat()));
                new dvdDAO().updateTD(2,list_DVDtemp.getModel().getElementAt(i).getDvdMa());
            }
            deleteValueThue();
            listHoaDon = new HoaDonDAO().getListHoaDon();
            listCTHD = new cthdDAO().getListCTHD();
            JOptionPane.showMessageDialog(null, "Cho thuê thành công!");
        }
    }//GEN-LAST:event_btnSearch4ActionPerformed

    private void txtSoNgayMuonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoNgayMuonKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoNgayMuonKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int qes = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa?", "Sửa Khách Hàng", JOptionPane.YES_NO_OPTION);
        if (qes != JOptionPane.YES_OPTION) {
            return;
        }
        String maKh = txtMaKH.getText().trim();
        String tenkh = txtTenKH.getText().trim();
        String quytactenkh = "^[A-Za-zÁÀẠÃẢáàạãảĂẮẰẶẴẲăắặẵẳÂẤẦẪẨâấầậẫẩđĐđéèẹẽẻÉÈẸẺẼêếềệễểÊẾỀỆỄỂíìịĩỉÍÌỊĨỈóòọõỏÓÒỌÕỎôốồộỗổÔỐỒỘỔỖơớờợởỡƠỚỜỢỠỞúùụủũÚÙỤỦŨưứừựữửƯỨỪỰỮỬýỳỵỷỹÝỲỴỶỸ ]{1,50}$";
        if (!kiemTraChuoi(tenkh, quytactenkh)) {
            JOptionPane.showMessageDialog(null, "Tên khách hàng: chỉ chứa ký tự chữ và không để trống!!!");
            return;
        }
        String sdt = txtSDT.getText().trim();
        String diachi = txtDiaChi.getText().trim();
        String quytacdiachi = "^[A-Za-z0-9ÁÀẠÃẢáàạãảĂẮẰẶẴẲăắặẵẳÂẤẦẪẨâấầậẫẩđĐđéèẹẽẻÉÈẸẺẼêếềệễểÊẾỀỆỄỂíìịĩỉÍÌỊĨỈóòọõỏÓÒỌÕỎôốồộỗổÔỐỒỘỔỖơớờợởỡƠỚỜỢỠỞúùụủũÚÙỤỦŨưứừựữửƯỨỪỰỮỬýỳỵỷỹÝỲỴỶỸ ,.-]{1,100}$";
        if (!kiemTraChuoi(diachi, quytacdiachi)) {
            JOptionPane.showMessageDialog(null, "Địa chỉ: Không bỏ trống và không chứa ký tự đặc biệt!!!");
            return;
        }
        String quytacsdt = "^[0-9]{10}$";
        if (!kiemTraChuoi(sdt, quytacsdt)) {
            JOptionPane.showMessageDialog(null, "Số điện thoại: chứa 10 ký tự số!!!");
            return;
        }

        KhachHang kh = new KhachHang(maKh, tenkh, diachi, sdt);
        if (kh != null) {
            if (khdao.updateKH(kh)) {
                JOptionPane.showMessageDialog(null, "Sửa thành công!!!");
                dal_ChinhSuaKhachHang.setVisible(false);
                showKhachHang();
            }

        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtTenKHThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKHThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHThemActionPerformed

    private void btn_LuuThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LuuThemKHActionPerformed

        String maKh = (khdao.listKH().size() + 1) + "";
        String tenkh = txtTenKHThem.getText().trim();
        String quytactenkh = "^[A-Za-zÁÀẠÃẢáàạãảĂẮẰẶẴẲăắặẵẳÂẤẦẪẨâấầậẫẩđĐđéèẹẽẻÉÈẸẺẼêếềệễểÊẾỀỆỄỂíìịĩỉÍÌỊĨỈóòọõỏÓÒỌÕỎôốồộỗổÔỐỒỘỔỖơớờợởỡƠỚỜỢỠỞúùụủũÚÙỤỦŨưứừựữửƯỨỪỰỮỬýỳỵỷỹÝỲỴỶỸ ]{1,50}$";
        if (!kiemTraChuoi(tenkh, quytactenkh)) {
            JOptionPane.showMessageDialog(null, "Tên khách hàng: chỉ chứa ký tự chữ và không để trống!!!");
            return;
        }
        String sdt = txtSDTThem.getText().trim();
        String diachi = txtDiaChiThem.getText().trim();
        String quytacdiachi = "^[A-Za-z0-9ÁÀẠÃẢáàạãảĂẮẰẶẴẲăắặẵẳÂẤẦẪẨâấầậẫẩđĐđéèẹẽẻÉÈẸẺẼêếềệễểÊẾỀỆỄỂíìịĩỉÍÌỊĨỈóòọõỏÓÒỌÕỎôốồộỗổÔỐỒỘỔỖơớờợởỡƠỚỜỢỠỞúùụủũÚÙỤỦŨưứừựữửƯỨỪỰỮỬýỳỵỷỹÝỲỴỶỸ ,.-]{1,100}$";
        if (!kiemTraChuoi(diachi, quytacdiachi)) {
            JOptionPane.showMessageDialog(null, "Địa chỉ: Không bỏ trống và không chứa ký tự đặc biệt!!!");
            return;
        }
        String quytacsdt = "^[0-9]{10}$";
        if (!kiemTraChuoi(sdt, quytacsdt)) {
            JOptionPane.showMessageDialog(null, "Số điện thoại: chứa 10 ký tự số!!!");
            return;
        }
        KhachHang kh = new KhachHang(maKh, tenkh, diachi, sdt, true);

        if (kh != null) {
            if (khdao.createKH(kh)) {
                JOptionPane.showMessageDialog(null, "Thêm thành công!!!");
                dal_ThemKH.setVisible(false);
                showKhachHang();
            }

        }
    }//GEN-LAST:event_btn_LuuThemKHActionPerformed

    private void jLabel30MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel30MouseDragged

    private void jLabel30MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel30MousePressed

    private void jPanel28MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel28MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel28MouseDragged

    private void jPanel28MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel28MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel28MousePressed

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed
        String tenTD = txtSearch1.getText().trim();
        if (tenTD.equals("")) {
            showTitle();
        }
        listTD = tddao.findTDByTen(tenTD);
        modelTieuDe.removeAllElements();

        for (int i = 0; i < listTD.size(); i++) {
            modelTieuDe.addElement(listTD.get(i));
        }
        list_TieuDe_Manager.setModel(modelTieuDe);
        list_TieuDe_Manager.setCellRenderer(new formTieuDe());
    }//GEN-LAST:event_btnSearch1ActionPerformed

    private void btnThemTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTDActionPerformed
        txtTenTD1.setText("");
        txtNgaySX1.setText("");
        txtNhaSX1.setText("");
        txtHinhAnh1.setText("");
        dal_ThemTD.setVisible(true);
    }//GEN-LAST:event_btnThemTDActionPerformed

    private void btnHinhAnh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinhAnh1ActionPerformed
        dal_File.showOpenDialog(null);
    }//GEN-LAST:event_btnHinhAnh1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String maTD =  (tddao.getListTD().size() + 1) + "";
        String ten = txtTenTD1.getText().trim();
        String quytactenkh = "^[A-Za-zÁÀẠÃẢáàạãảĂẮẰẶẴẲăắặẵẳÂẤẦẪẨâấầậẫẩđĐđéèẹẽẻÉÈẸẺẼêếềệễểÊẾỀỆỄỂíìịĩỉÍÌỊĨỈóòọõỏÓÒỌÕỎôốồộỗổÔỐỒỘỔỖơớờợởỡƠỚỜỢỠỞúùụủũÚÙỤỦŨưứừựữửƯỨỪỰỮỬýỳỵỷỹÝỲỴỶỸ ]{1,50}$";
        if (!kiemTraChuoi(ten, quytactenkh)) {
            JOptionPane.showMessageDialog(null, "Tên khách hàng: chỉ chứa ký tự chữ và không để trống!!!");
            return;
        }
        String nhaSX = txtNhaSX1.getText().trim();
        String quytacnhasx = "^[A-Za-z0-9ÁÀẠÃẢáàạãảĂẮẰẶẴẲăắặẵẳÂẤẦẪẨâấầậẫẩđĐđéèẹẽẻÉÈẸẺẼêếềệễểÊẾỀỆỄỂíìịĩỉÍÌỊĨỈóòọõỏÓÒỌÕỎôốồộỗổÔỐỒỘỔỖơớờợởỡƠỚỜỢỠỞúùụủũÚÙỤỦŨưứừựữửƯỨỪỰỮỬýỳỵỷỹÝỲỴỶỸ ]{1,100}$";
        if (!kiemTraChuoi(nhaSX, quytacnhasx)) {
            JOptionPane.showMessageDialog(null, "Nhà sản xuất: Không bỏ trống và không chứa ký tự đặc biệt!!!");
            return;
        }
        String loai = (String) cbbDVD1.getSelectedItem();
        Loai loaiTD = null;
        if (loai.equalsIgnoreCase("Phim")) {
            loaiTD = tddao.findLoaiTD("1");
        } else {
            loaiTD = tddao.findLoaiTD("2");
        }

        String ngay = txtNgaySX1.getText().trim();
        String quytacngay = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        if (!kiemTraChuoi(ngay, quytacngay)) {
            JOptionPane.showMessageDialog(null, "Ngày sản xuất phải có định dạng dd/MM/YYYY");
            return;
        }
        String img = txtHinhAnh1.getText().trim();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formatter.parse(ngay);
            TieuDe td = new TieuDe(maTD, ten, nhaSX, loaiTD, date, img,true);
            if (td != null) {
                if (tddao.createTD(td)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!!!");
                    dal_ThemTD.setVisible(false);
                    showTitle();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel39MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel39MouseDragged

    private void jLabel39MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel39MousePressed

    private void jPanel40MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel40MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel40MouseDragged

    private void jPanel40MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel40MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel40MousePressed

    private void btnHinhAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinhAnhActionPerformed
        dal_File.showOpenDialog(null);
    }//GEN-LAST:event_btnHinhAnhActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int qes = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa?", "Sửa Tiêu Đề", JOptionPane.YES_NO_OPTION);
        if (qes != JOptionPane.YES_OPTION) {
            return;
        }
        String maTD = txtMaTD.getText().trim();
        String ten = txtTenTD.getText().trim();
        String quytactenkh = "^[A-Za-zÁÀẠÃẢáàạãảĂẮẰẶẴẲăắặẵẳÂẤẦẪẨâấầậẫẩđĐđéèẹẽẻÉÈẸẺẼêếềệễểÊẾỀỆỄỂíìịĩỉÍÌỊĨỈóòọõỏÓÒỌÕỎôốồộỗổÔỐỒỘỔỖơớờợởỡƠỚỜỢỠỞúùụủũÚÙỤỦŨưứừựữửƯỨỪỰỮỬýỳỵỷỹÝỲỴỶỸ ]{1,50}$";
        if (!kiemTraChuoi(ten, quytactenkh)) {
            JOptionPane.showMessageDialog(null, "Tên khách hàng: chỉ chứa ký tự chữ và không để trống!!!");
            return;
        }
        String nhaSX = txtNhaSX.getText().trim();
        String quytacnhasx = "^[A-Za-z0-9ÁÀẠÃẢáàạãảĂẮẰẶẴẲăắặẵẳÂẤẦẪẨâấầậẫẩđĐđéèẹẽẻÉÈẸẺẼêếềệễểÊẾỀỆỄỂíìịĩỉÍÌỊĨỈóòọõỏÓÒỌÕỎôốồộỗổÔỐỒỘỔỖơớờợởỡƠỚỜỢỠỞúùụủũÚÙỤỦŨưứừựữửƯỨỪỰỮỬýỳỵỷỹÝỲỴỶỸ ]{1,100}$";
        if (!kiemTraChuoi(nhaSX, quytacnhasx)) {
            JOptionPane.showMessageDialog(null, "Nhà sản xuất: Không bỏ trống và không chứa ký tự đặc biệt!!!");
            return;
        }
        String loai = (String) cbbDVD.getSelectedItem();
        Loai loaiTD = null;
        if (loai.equalsIgnoreCase("Phim")) {
            loaiTD = tddao.findLoaiTD("1");
        } else {
            loaiTD = tddao.findLoaiTD("2");
        }

        String ngay = txtNgaySX.getText().trim();
        String quytacngay = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        if (!kiemTraChuoi(ngay, quytacngay)) {
            JOptionPane.showMessageDialog(null, "Ngày sản xuất phải có định dạng dd/MM/YYYY");
            return;
        }
        String img = txtHinhAnh.getText().trim();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formatter.parse(ngay);
            TieuDe td = new TieuDe(maTD, ten, nhaSX, loaiTD, date, img, false);
            if (td != null) {
                if (tddao.updateTD(td)) {
                    JOptionPane.showMessageDialog(null, "Sửa thành công!!!");
                    dal_ChinhSuaTieuDe.setVisible(false);
                    showTitle();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel9MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseDragged

    }//GEN-LAST:event_jLabel9MouseDragged

    private void jLabel9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MousePressed

    }//GEN-LAST:event_jLabel9MousePressed

    private void jPanel10MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseDragged

    }//GEN-LAST:event_jPanel10MouseDragged

    private void jPanel10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MousePressed

    }//GEN-LAST:event_jPanel10MousePressed

    private void DangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DangNhapActionPerformed
        this.setVisible(false);
        formDangNhap i = new formDangNhap();
        i.setVisible(true);
    }//GEN-LAST:event_DangNhapActionPerformed

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        dal_ThemKH.setVisible(true);
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jLabel45MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel45MouseDragged

    private void jLabel45MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel45MousePressed

    private void jPanel47MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel47MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel47MouseDragged

    private void jPanel47MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel47MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel47MousePressed

    private void txtSearch_KHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearch_KHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearch_KHActionPerformed

    private void btnThemDVDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDVDActionPerformed
        // TODO add your handling code here:
        them_DVD.setVisible(true);
        
    }//GEN-LAST:event_btnThemDVDActionPerformed

    private void cbTenTieuDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTenTieuDeActionPerformed
        // TODO add your handling code here:
      listTD=tddao.getListTD();
      comboboxModelTenTD.removeAllElements();
      
        for(TieuDe t:listTD){       
      comboboxModelTenTD.addElement(t.getTdTenTD());
                       }
        cbTenTieuDe=new JComboBox(comboboxModelTenTD);
    }//GEN-LAST:event_cbTenTieuDeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CleckUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CleckUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CleckUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CleckUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CleckUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DVD_Container;
    private javax.swing.JButton DangNhap;
    private javax.swing.JPopupMenu MoOption_DVDThue;
    private javax.swing.JPopupMenu MoOption_KhachHang;
    private javax.swing.JPopupMenu MoOption_TieuDe;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnClose1;
    private javax.swing.JButton btnHinhAnh;
    private javax.swing.JButton btnHinhAnh1;
    private javax.swing.JButton btnQuanLyChiPhi;
    private javax.swing.JButton btnQuanLyChoThue;
    private javax.swing.JButton btnQuanLyDatTruoc;
    private javax.swing.JButton btnQuanLyKhachHang;
    private javax.swing.JButton btnQuanLyKhoDia;
    private javax.swing.JButton btnQuanLyTieuDe;
    private javax.swing.JButton btnQuanLyTraDia;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnSearch4;
    private javax.swing.JButton btnSearch_KH;
    private javax.swing.JButton btnSearch_KH1;
    private javax.swing.JButton btnSearch_KH2;
    private javax.swing.JButton btnSearch_KH3;
    private javax.swing.JButton btnThemDVD;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemTD;
    private javax.swing.JButton btn_CheckDVD;
    private javax.swing.JButton btn_CheckKH;
    private javax.swing.JButton btn_CheckKH1;
    private javax.swing.JButton btn_CheckKH2;
    private javax.swing.JButton btn_LuuThemKH;
    private javax.swing.JComboBox<String> cbTenTieuDe;
    private javax.swing.JCheckBox cb_TTKH;
    private javax.swing.JComboBox<String> cbbDVD;
    private javax.swing.JComboBox<String> cbbDVD1;
    private javax.swing.JPanel codated;
    private javax.swing.JPanel container;
    private javax.swing.JPanel container_Content;
    private javax.swing.JDialog dal_ChinhSuaKhachHang;
    private javax.swing.JDialog dal_ChinhSuaTieuDe;
    private javax.swing.JPanel dal_Container;
    private javax.swing.JPanel dal_Container1;
    private javax.swing.JPanel dal_Container2;
    private javax.swing.JPanel dal_Container4;
    private javax.swing.JPanel dal_Container5;
    private javax.swing.JDialog dal_DVD;
    private javax.swing.JFileChooser dal_File;
    private javax.swing.JDialog dal_PhiTre;
    private javax.swing.JDialog dal_ThemKH;
    private javax.swing.JDialog dal_ThemTD;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    public static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lb_TongTien;
    private javax.swing.JLabel lb_infoMaKH;
    private javax.swing.JLabel lb_infoTenKH;
    private javax.swing.JList<Dvd> list_DVD;
    private javax.swing.JList<Dvd> list_DVDtemp;
    private javax.swing.JList<KhachHang> list_KhachHang;
    private javax.swing.JList<ChiTietHoaDon> list_PhiTreHanThue;
    private javax.swing.JList<TieuDe> list_TieuDe_Manager;
    private javax.swing.JList<HoaDon> list_TraDia;
    private javax.swing.JList<HoaDon> list_TraDia1;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlQuanLyChiphi;
    private javax.swing.JPanel pnlQuanLyChoThue;
    private javax.swing.JPanel pnlQuanLyDatTruoc;
    private javax.swing.JPanel pnlQuanLyKhachHang;
    private javax.swing.JPanel pnlQuanLyKhoDia;
    private javax.swing.JPanel pnlQuanLyTieuDe;
    private javax.swing.JPanel pnlQuanLyTraDia;
    private javax.swing.JPanel pnl_btn_QuanLyChiPhi;
    private javax.swing.JPanel pnl_btn_QuanLyChoThue;
    private javax.swing.JPanel pnl_btn_QuanLyDatTruoc;
    private javax.swing.JPanel pnl_btn_QuanLyKhachHang;
    public static javax.swing.JPanel pnl_btn_QuanLyKhoDia;
    public static javax.swing.JPanel pnl_btn_QuanLyTieuDe;
    private javax.swing.JPanel pnl_btn_QuanLyTraDia;
    private javax.swing.JPanel pnl_hl1;
    private javax.swing.JPanel pnl_hl2;
    private javax.swing.JPanel pnl_hl3;
    private javax.swing.JPanel pnl_hl4;
    private javax.swing.JPanel pnl_hl5;
    private javax.swing.JPanel pnl_hl6;
    private javax.swing.JPanel pnl_hl7;
    private javax.swing.JPanel pnl_sp1;
    private javax.swing.JPanel pnl_sp2;
    private javax.swing.JPanel pnl_sp3;
    private javax.swing.JPanel pnl_sp4;
    private javax.swing.JPanel pnl_sp5;
    private javax.swing.JPanel pnl_sp6;
    private javax.swing.JPanel pnl_sp7;
    private javax.swing.JMenuItem popChangeStatus;
    public static javax.swing.JMenuItem popChangeStatus1;
    private javax.swing.JMenuItem popChinhSua;
    private javax.swing.JMenuItem popChinhSua1;
    private javax.swing.JMenuItem popLoaiBo;
    private javax.swing.JLabel sdMenu;
    private javax.swing.JDialog them_DVD;
    public static javax.swing.JTextField txtDiaChi;
    public static javax.swing.JTextField txtDiaChiThem;
    public static javax.swing.JTextField txtHinhAnh;
    public static javax.swing.JTextField txtHinhAnh1;
    public static javax.swing.JTextField txtMaDVD;
    public static javax.swing.JTextField txtMaKH;
    public static javax.swing.JTextField txtMaTD;
    public static javax.swing.JTextField txtNgaySX;
    public static javax.swing.JTextField txtNgaySX1;
    public static javax.swing.JTextField txtNhaSX;
    public static javax.swing.JTextField txtNhaSX1;
    public static javax.swing.JTextField txtSDT;
    public static javax.swing.JTextField txtSDTThem;
    private javax.swing.JTextField txtSearch1;
    private javax.swing.JTextField txtSearchIDDVD;
    private javax.swing.JTextField txtSearchKH_Thue;
    private javax.swing.JTextField txtSearch_DVD;
    private javax.swing.JTextField txtSearch_KH;
    private javax.swing.JTextField txtSearch_KH1;
    private javax.swing.JTextField txtSearch_KH2;
    private javax.swing.JTextField txtSoNgayMuon;
    public static javax.swing.JTextField txtTenKH;
    public static javax.swing.JTextField txtTenKHThem;
    public static javax.swing.JTextField txtTenTD;
    public static javax.swing.JTextField txtTenTD1;
    // End of variables declaration//GEN-END:variables
}
