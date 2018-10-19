package knife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.URL;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import burp.BurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.IContextMenuInvocation;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;

public class AddHostToScopeMenu extends JMenuItem {//JMenuItem vs. JMenu

    public AddHostToScopeMenu(BurpExtender burp){
        this.setText("Hack bar -- Add host to scope");
        this.addActionListener(new AddHostToScope_Action(burp,burp.context));
    }
}



class AddHostToScope_Action implements ActionListener{
	//scope matching is actually String matching!!
	private IContextMenuInvocation invocation;
    public BurpExtender myburp;
	public IExtensionHelpers helpers;
	public PrintWriter stdout;
	public PrintWriter stderr;
	public IBurpExtenderCallbacks callbacks;
	public AddHostToScope_Action(BurpExtender burp,IContextMenuInvocation invocation) {
		this.invocation  = invocation;
        this.helpers = burp.helpers;
        this.callbacks = burp.callbacks;
        this.stderr = burp.stderr;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
    {
       try{
        	IHttpRequestResponse[] messages = invocation.getSelectedMessages();
        	for(IHttpRequestResponse message:messages) {
        		String url = message.getHttpService().toString();
				URL shortUrl = new URL(url);
	        	callbacks.includeInScope(shortUrl);
        	}
        }
        catch (Exception e1)
        {
            e1.printStackTrace(stderr);
        }
    }
}