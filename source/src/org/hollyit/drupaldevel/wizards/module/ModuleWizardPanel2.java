/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hollyit.drupaldevel.wizards.module;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.filesystems.FileObject;
import java.io.File;
import org.hollyit.drupaldevel.DrupalDevelTool;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbPreferences;

public class ModuleWizardPanel2 implements WizardDescriptor.Panel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private ModuleVisualPanel2 component;
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);
    private String moduleName;
    private String moduleSafeName;
    private FileObject target;
    private String drupalVer = NbPreferences.forModule(DrupalDevelTool.class).get("drupalVersion", "");
    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.

    public ModuleWizardPanel2(FileObject target) {

        this.target = target;
    }

    public ModuleVisualPanel2 getComponent() {
        if (component == null) {
            component = new ModuleVisualPanel2(this);

            component.setFolderPath(this.target.getPath());
        }
        return component;
    }

    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx(SampleWizardPanel1.class);
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public String getModuleSafeName() {
        return this.moduleSafeName;
    }

    public String getDrupalVer() {
        return this.drupalVer;
    }

    public ArrayList getFileList() {
        return this.getComponent().getFileList();
    }

    public boolean isValid() {
        if ((this.moduleName != null) && (this.moduleName.length() > 0)) {
            String path = this.target.getPath() + "/" + this.moduleSafeName;
            File file = new File(path);
            if (!file.exists()) {
                this.getComponent().hideWarning();
                return true;
            } else {
                this.getComponent().showWarning(this.moduleSafeName);
            }

        }

        return false;
        // If it depends on some condition (form filled out...), then:
        // return someCondition();
        // and when this condition changes (last form field filled in...) then:
        // fireChangeEvent();
        // and uncomment the complicated stuff below.
    }

    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    protected final void fireChangeEvent(String moduleName, String moduleSafeName) {
        this.moduleName = moduleName;
        this.moduleSafeName = moduleSafeName;
        Set<ChangeListener> ls;
        synchronized (listeners) {
            ls = new HashSet<ChangeListener>(listeners);
        }
        ChangeEvent ev = new ChangeEvent(this);
        for (ChangeListener l : ls) {
            l.stateChanged(ev);
        }
    }
    /*
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0
    public final void addChangeListener(ChangeListener l) {
    synchronized (listeners) {
    listeners.add(l);
    }
    }
    public final void removeChangeListener(ChangeListener l) {
    synchronized (listeners) {
    listeners.remove(l);
    }
    }
    protected final void fireChangeEvent() {
    Iterator<ChangeListener> it;
    synchronized (listeners) {
    it = new HashSet<ChangeListener>(listeners).iterator();
    }
    ChangeEvent ev = new ChangeEvent(this);
    while (it.hasNext()) {
    it.next().stateChanged(ev);
    }
    }
     */

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    public void readSettings(Object settings) {
    }

    public void storeSettings(Object settings) {
    }
}
