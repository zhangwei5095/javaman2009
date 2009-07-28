package org.ndot.cclovepp.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.DefaultUndoManager;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.hsqldb.Server;
import org.ndot.cclovepp.db.document.PersistentDocument;
import org.ndot.cclovepp.db.document.config.SqlConfig;
import org.ndot.cclovepp.db.document.listener.SqlTextListener;
import org.ndot.cclovepp.db.document.resource.ResourceManager;
import org.ndot.cclovepp.db.event.EventManager;
import org.ndot.cclovepp.db.table.TableContentProvider;
import org.ndot.cclovepp.db.table.TableLabelProvider;
import org.ndot.cclovepp.db.tree.TreeContentProvider;
import org.ndot.cclovepp.db.tree.TreeElement;
import org.ndot.cclovepp.db.tree.TreeLabelProvider;
import org.ndot.cclovepp.db.util.Constants;
import org.ndot.cclovepp.db.util.WidgetTool;

import com.swtdesigner.SWTResourceManager;

public class NDotDB extends ApplicationWindow {

	private StyledText styledText;

	private SourceViewer sqlSourceViewer;

	private Table table;

	private SashForm hsashForm;

	private CTabFolder cTabFolder;

	private CTabItem dbconfigItem;

	private CTabItem curdbItem;

	private TreeViewer dbConfigTreeViewer;

	private Composite dbConfigComposite;
	private Composite dbconnectComposite;
	private TreeViewer dbconnectTreeViewer;

	private SashForm vsashForm;

	private TableViewer resultTableViewer;

	private SqlConfig sqlConfig;

	private EventManager eventManager;

	private IUndoManager undoManager;// �������ظ�������

	private PersistentDocument document;// �����ĵ�����
	
	private SqlTextListener sqlListener;

	private Connection conn;

	private DatabaseMetaData dMeta;

	private ResultSet rst;

	private Statement st;
	
	private HashMap<String, Server> configServies;
	private Server curServie=null;




	public HashMap<String, Server> getConfigServies() {
		return configServies;
	}


	public void setConfigServies(HashMap<String, Server> configServies) {
		this.configServies = configServies;
	}


	public Server getCurServie() {
		return curServie;
	}

	public void setCurServie(Server curServie) {
		this.curServie = curServie;
	}

	public Composite getDbconnectComposite() {
		return dbconnectComposite;
	}

	public void setDbconnectComposite(Composite dbconnectComposite) {
		this.dbconnectComposite = dbconnectComposite;
	}

	public TreeViewer getDbconnectTreeViewer() {
		return dbconnectTreeViewer;
	}

	public void setDbconnectTreeViewer(TreeViewer dbconnectTreeViewer) {
		this.dbconnectTreeViewer = dbconnectTreeViewer;
	}

	public DatabaseMetaData getDMeta() {
		return dMeta;
	}

	public void setDMeta(DatabaseMetaData meta) {
		dMeta = meta;
	}

	public SqlTextListener getSqlListener() {
		return sqlListener;
	}

	public void setSqlListener(SqlTextListener sqlListener) {
		this.sqlListener = sqlListener;
	}

	/**
	 * Create the application window
	 */
	public NDotDB() {
		super(null);
		eventManager = new EventManager(this);
		createActions();
		addCoolBar(SWT.FLAT);
		addMenuBar();
		addStatusLine();
		this.configServies = WidgetTool.getConfigServers();	
		

	}

	

	/**
	 * Create contents of the application window
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		// ��ʼ���ĵ�����
		document = new PersistentDocument();
		hsashForm = new SashForm(container, SWT.BORDER);

		cTabFolder = new CTabFolder(hsashForm, SWT.NONE);

		dbconfigItem = new CTabItem(cTabFolder, SWT.NONE);
		dbconfigItem.setText("���ݿ�����");

		curdbItem = new CTabItem(cTabFolder, SWT.NONE);		
		curdbItem.setText("��ǰ����");
		
		dbconnectComposite =new Composite(cTabFolder, SWT.BORDER);
		dbconnectComposite.setLayout(new FillLayout());
		
		dbconnectTreeViewer = new TreeViewer(dbconnectComposite, SWT.BORDER);
		dbconnectTreeViewer.setContentProvider(new TreeContentProvider());
		dbconnectTreeViewer.setLabelProvider(new TreeLabelProvider());
		
//		dbconnectTreeViewer.setInput(WidgetTool.getConnectTree(dMeta));
		
		curdbItem.setControl(dbconnectComposite);
		dbConfigComposite = new Composite(cTabFolder, SWT.BORDER);
		
		dbConfigComposite.setLayout(new FillLayout());
		dbconfigItem.setControl(dbConfigComposite);
		
		// ����ֱ����ʾ��item������ֻ��������һ�²���ʾ
		cTabFolder.setSelection(dbconfigItem);
		dbConfigTreeViewer = new TreeViewer(dbConfigComposite, SWT.BORDER);
		dbConfigTreeViewer.setContentProvider(new TreeContentProvider());
		dbConfigTreeViewer.setLabelProvider(new TreeLabelProvider());
		dbConfigTreeViewer.setInput(WidgetTool.getDBConfig());

		//���ò˵�
		createContextMenu(parent);
		vsashForm = new SashForm(hsashForm, SWT.BORDER | SWT.VERTICAL);

		CompositeRuler ruler = new CompositeRuler();
		LineNumberRulerColumn lineCol = new LineNumberRulerColumn();
		ruler.addDecorator(0, lineCol);
		sqlSourceViewer = new SourceViewer(vsashForm, ruler, SWT.BORDER);
		styledText = sqlSourceViewer.getTextWidget();
		styledText.setForeground(SWTResourceManager.getColor(128, 0, 255));
		sqlListener = new SqlTextListener(styledText);
		
//		styledText.addModifyListener(sqlListener);
		
		sqlConfig = new SqlConfig(this);
		// ����������sqlConfig,������document,�﷨��ɫ���ܲ���Ч
		sqlSourceViewer.configure(sqlConfig);
		sqlSourceViewer.setDocument(document);
		document.set("select * from students s");

		undoManager = new DefaultUndoManager(100);// ��ʼ����������������Ĭ�Ͽɳ���100��
		undoManager.connect(sqlSourceViewer);// ���ó���������Ӧ�����ĵ�

		initCodeFont();


		resultTableViewer = new TableViewer(vsashForm, SWT.BORDER);
		table = resultTableViewer.getTable();
		table.setFont(SWTResourceManager.getFont("", 10, SWT.NONE));

		hsashForm.setWeights(new int[] { 25, 75 });

		vsashForm.setWeights(new int[] { 25, 75 });
		// ��������
		resultTableViewer.setContentProvider(new TableContentProvider());
		// ������ͼ
		resultTableViewer.setLabelProvider(new TableLabelProvider());

//		 setTableViewer();
		return container;
	}

	public void setTableViewer() {
		try {
			resultTableViewer.getTable().clearAll();
			TableColumn[] tcs = resultTableViewer.getTable().getColumns();
			for (int i = 0; i < tcs.length; i++) {				
				System.out.println("ɾ���У�"+": "+i+" : "+tcs[i].getText());
				resultTableViewer.remove(tcs[i]);
			}
			String[] head = WidgetTool.getTableHead(rst);
			List<String[]> te = WidgetTool.getTableElement(rst);
			// ����������
			for (int i = 0; i < head.length; i++) {
				new TableColumn(resultTableViewer.getTable(), SWT.LEFT)
						.setText(head[i]);
				
				resultTableViewer.getTable().getColumn(i).pack();
			}
			// ��������
			resultTableViewer.setInput(te);
			// ���ñ�ͷ�ͱ���߿ɼ�
			resultTableViewer.getTable().setHeaderVisible(true);
			resultTableViewer.getTable().setLinesVisible(true);
			resultTableViewer.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ʼ�����������
	private void initCodeFont() {
		// ����һ��Ĭ�ϵ�����
		FontData defaultFont = new FontData("Courier New", 10, SWT.NORMAL);
		// �������ѡ��������������쳣����ʹ��Ĭ�ϵ�����
		FontData data = StringConverter.asFontData(ResourceManager
				.getPreferenceStore().getString(Constants.CODE_FONT),
				defaultFont);
		// ��������
		Font font = new Font(this.getShell().getDisplay(), data);
		sqlSourceViewer.getTextWidget().setFont(font);// ��������
	}

	/**
	 * Create the actions
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager
	 * 
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		return WidgetTool.createMenu(this);
	}

	/**
	 * Create the coolbar manager
	 * 
	 * @return the coolbar manager
	 */
	@Override
	protected CoolBarManager createCoolBarManager(int style) {
		return WidgetTool.createCoolBar(this, style);
	}

	/**
	 * Create the status line manager
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		statusLineManager.setMessage(null, "sadsad");
		return statusLineManager;
	}

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			NDotDB window = new NDotDB();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �����������ݿ�Ĳ˵�
	 * 
	 * @param parent
	 */

	private void createContextMenu(Composite parent) {		
		//�������ݿ������������˵�
		dbConfigTreeViewer.getTree().setMenu(WidgetTool.createDBContextMenu(this, parent));
		//�������ݱ����˵�
		this.dbconnectTreeViewer.getTree().setMenu(WidgetTool.createTableMenu(this, parent));
	}
	/**
	 * Configure the shell
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("NDot Database Manager - ���ߣ�����");
		newShell.setSize(new Point(900, 700));
	}

	/**
	 * Return the initial size of the window
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(486, 404);
	}

	public SashForm getHsashForm() {
		return hsashForm;
	}

	public void setHsashForm(SashForm hsashForm) {
		this.hsashForm = hsashForm;
	}

	public TableViewer getResultTableViewer() {
		return resultTableViewer;
	}

	public void setResultTableViewer(TableViewer resultTableViewer) {
		this.resultTableViewer = resultTableViewer;
	}

	public SqlConfig getSqlConfig() {
		return sqlConfig;
	}

	public void setSqlConfig(SqlConfig sqlConfig) {
		this.sqlConfig = sqlConfig;
	}

	public SourceViewer getSqlSourceViewer() {
		return sqlSourceViewer;
	}

	public void setSqlSourceViewer(SourceViewer sqlSourceViewer) {
		this.sqlSourceViewer = sqlSourceViewer;
	}

	public StyledText getStyledText() {
		return styledText;
	}

	public void setStyledText(StyledText styledText) {
		this.styledText = styledText;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public TreeViewer getTreeViewer() {
		return dbConfigTreeViewer;
	}

	public void setTreeViewer(TreeViewer treeViewer) {
		this.dbConfigTreeViewer = treeViewer;
	}

	public SashForm getVsashForm() {
		return vsashForm;
	}

	public void setVsashForm(SashForm vsashForm) {
		this.vsashForm = vsashForm;
	}

	public PersistentDocument getDocument() {
		return document;
	}

	public void setDocument(PersistentDocument document) {
		this.document = document;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	public IUndoManager getUndoManager() {
		return undoManager;
	}

	public void setUndoManager(IUndoManager undoManager) {
		this.undoManager = undoManager;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public CTabFolder getCTabFolder() {
		return cTabFolder;
	}

	public void setCTabFolder(CTabFolder tabFolder) {
		cTabFolder = tabFolder;
	}

	public CTabItem getCurdbItem() {
		return curdbItem;
	}

	public void setCurdbItem(CTabItem curdbItem) {
		this.curdbItem = curdbItem;
	}

	public Composite getDbConfigComposite() {
		return dbConfigComposite;
	}

	public void setDbConfigComposite(Composite dbConfigComposite) {
		this.dbConfigComposite = dbConfigComposite;
	}

	public CTabItem getDbconfigItem() {
		return dbconfigItem;
	}

	public void setDbconfigItem(CTabItem dbconfigItem) {
		this.dbconfigItem = dbconfigItem;
	}

	public TreeViewer getDbConfigTreeViewer() {
		return dbConfigTreeViewer;
	}

	public void setDbConfigTreeViewer(TreeViewer dbConfigTreeViewer) {
		this.dbConfigTreeViewer = dbConfigTreeViewer;
	}

	public ResultSet getRst() {
		return rst;
	}

	public void setRst(ResultSet rst) {
		this.rst = rst;
	}

	public Statement getSt() {
		return st;
	}

	public void setSt(Statement st) {
		this.st = st;
	}

}
