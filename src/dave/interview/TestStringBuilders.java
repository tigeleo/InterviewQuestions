package dave.interview;

public class TestStringBuilders {
	static final int TEST_LENGTH=100000000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//buildPlusNumbers();
		//buildBuilderNumbers();
		//buildBufferNumbers();
		//buildPlusNumbers();
		//buildBuilderNumbers();
		//buildBufferNumbers();
		
		//buildUrlPlus();
		//buildUrlBuilder();
		
		buildPlusStrings();
		buildAppendStrings();
	}
	
	
	
	public static void buildPlusStrings(){
		long st=System.currentTimeMillis();
		
		String x1="cszfjskldf";
		String x2="sdflskdfs";
		String x3="lakfdalkfla";
		String x4="aklfdsaljdl";
		String x5="lkadlkasdlaksdl";

		for(int i=0;i<=TEST_LENGTH;i++){
			String text=x1+x2+x3+x4+x5;

		}
		
		long et=System.currentTimeMillis();
		System.out.println("After buildPlusStrings run "+TEST_LENGTH+": "  + (et-st) + " millis");
	}
	
	public static void buildAppendStrings(){
		long st=System.currentTimeMillis();
		
		String x1="cszfjskldf";
		String x2="sdflskdfs";
		String x3="lakfdalkfla";
		String x4="aklfdsaljdl";
		String x5="lkadlkasdlaksdl";

		for(int i=0;i<=TEST_LENGTH;i++){
			StringBuilder text=new StringBuilder();
			text.append(x1);
			text.append(x2);
			text.append(x3);
			text.append(x4);
			text.append(x5);

			
			
		}
		
		long et=System.currentTimeMillis();
		System.out.println("After buildAppendStrings run "+TEST_LENGTH+": "  + (et-st) + " millis");
	}
		
	
	
	public static void buildPlusNumbers(){
		long st=System.currentTimeMillis();
		
		String text="";
		for(int i=0;i<=TEST_LENGTH;i++){
			text=text+i;
		}
		
		long et=System.currentTimeMillis();
		System.out.println("Plus take to build text ( "  + text.length() + "): "  + (et-st) + " millis");
	}
	
	public static void buildBuilderNumbers(){
		long st=System.currentTimeMillis();
		
		StringBuilder text=new StringBuilder();
		for(int i=0;i<=TEST_LENGTH;i++){
			text.append(i);
		}
		
		long et=System.currentTimeMillis();
		System.out.println("StringBuilder take to build text ( "  + text.length() + "): "  + (et-st) + " millis");
	}
	
	public static void buildUrlPlus(){
		long st=System.currentTimeMillis();
		
		String text="";
		String sterm="askdalksdjas";
		String sufixUrl="holding";
		String urlDomain="ww.gggg.com";
		for(int i=0;i<=TEST_LENGTH;i++){
			String url = "urlDomain" + "/search/" + sterm + "/" + sterm + "/1,1,1,E/"+sufixUrl+"&FF=" + sterm;
		}
		
		long et=System.currentTimeMillis();
		System.out.println("Plus take to build text ( "  + text.length() + "): "  + (et-st) + " millis");
	}
	
	public static void buildUrlBuilder(){
		long st=System.currentTimeMillis();
		
		String text="";
		String sterm="askdalksdjas";
		String sufixUrl="holding";
		String urlDomain="ww.gggg.com";
		for(int i=0;i<=TEST_LENGTH;i++){
			StringBuilder url = new StringBuilder( urlDomain);
			url.append("/search/");
			url.append(sterm);
			url.append( "/" );
			url.append( sterm) ;
			url.append("/1,1,1,E/");
			url.append(sufixUrl);
			url.append("&FF=");
			url.append(sterm);
			String urlb = url.toString();
		}
		
		long et=System.currentTimeMillis();
		System.out.println("Plus take to build text ( "  + text.length() + "): "  + (et-st) + " millis");
	}
	
	public static void buildBufferNumbers(){
		long st=System.currentTimeMillis();
		
		StringBuffer text=new StringBuffer();
		for(int i=0;i<=TEST_LENGTH;i++){
			text.append(i);
		}
		
		long et=System.currentTimeMillis();
		System.out.println("StringBuffer take to build text ( "  + text.length() + "): "  + (et-st) + " millis");
	}
	
	static boolean isEnable=false;
	public static void  testLogDebugOut(){
		long st=System.currentTimeMillis();
		
		StringBuffer text=new StringBuffer();
		for(int i=0;i<=TEST_LENGTH;i++){
			
			if(isEnable) debug("Excention ocuued");
		}
		
		long et=System.currentTimeMillis();
		System.out.println("testLogDebugOut take: "  + (et-st) + " millis");
		
	}
	
	public static void  testLogDebugIn(){
		long st=System.currentTimeMillis();
		
		StringBuffer text=new StringBuffer();
		for(int i=0;i<=TEST_LENGTH;i++){
			
			debug("Excention ocuued");
		}
		
		long et=System.currentTimeMillis();
		System.out.println("testLogDebugIn take: "  + (et-st) + " millis");
		
	}
	
	public static void debug(String msg){
		if(isEnable){
			System.err.println(msg);
		}
	}
}
