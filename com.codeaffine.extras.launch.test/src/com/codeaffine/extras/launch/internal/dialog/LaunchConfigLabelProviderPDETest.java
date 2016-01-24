package com.codeaffine.extras.launch.internal.dialog;

import static com.codeaffine.extras.launch.internal.dialog.LaunchConfigLabelProvider.LabelMode.DETAIL;
import static com.codeaffine.extras.launch.internal.dialog.LaunchConfigLabelProvider.LabelMode.LIST;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import com.codeaffine.eclipse.swt.test.util.DisplayHelper;
import com.codeaffine.extras.launch.internal.dialog.LaunchConfigLabelProvider.LabelMode;
import com.codeaffine.extras.launch.test.LaunchConfigRule;

public class LaunchConfigLabelProviderPDETest {

  @Rule
  public final LaunchConfigRule launchConfigRule = new LaunchConfigRule();
  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();

  private Collection<LaunchConfigLabelProvider> labelProviders;

  @After
  public void tearDown() {
    for( LaunchConfigLabelProvider labelProvider : labelProviders ) {
      labelProvider.dispose();
    }
  }

  @Test
  public void testGetImage() throws CoreException {
    ILaunchConfigurationWorkingCopy launchConfig = launchConfigRule.createLaunchConfig();
    LaunchConfigLabelProvider labelProvider = createLabelProvider( LIST );

    Image image = labelProvider.getImage( launchConfig );

    assertThat( image ).isNotNull();
  }

  @Test
  public void testGetImageForArbitraryObject() {
    LaunchConfigLabelProvider labelProvider = createLabelProvider( LIST );

    Image image = labelProvider.getImage( new Object() );

    assertThat( image ).isNull();
  }

  @Test
  public void testGetListText() throws CoreException {
    ILaunchConfigurationWorkingCopy launchConfig = launchConfigRule.createLaunchConfig();
    LaunchConfigLabelProvider labelProvider = createLabelProvider( LIST );

    String text = labelProvider.getText( launchConfig );

    assertThat( text ).isEqualTo( launchConfig.getName() );
  }

  @Test
  public void testGetDetailText() throws CoreException {
    ILaunchConfigurationWorkingCopy launchConfig = launchConfigRule.createLaunchConfig();
    LaunchConfigLabelProvider labelProvider = createLabelProvider( DETAIL );

    String text = labelProvider.getText( launchConfig );

    assertThat( text ).isEqualTo( launchConfig.getName() + " - " + launchConfig.getType().getName() );
  }

  @Test
  public void testGetTextForNullArgument() {
    LaunchConfigLabelProvider labelProvider = createLabelProvider( LIST );

    String text = labelProvider.getText( null );

    assertThat( text ).isNotNull();
  }

  @Test
  public void testGetTextForArbitraryObject() {
    LaunchConfigLabelProvider labelProvider = createLabelProvider( LIST );

    String text = labelProvider.getText( new Object() );

    assertThat( text ).isNotNull();
  }

  private LaunchConfigLabelProvider createLabelProvider( LabelMode labelMode ) {
    labelProviders = new ArrayList<>();
    Shell shell = displayHelper.createShell();
    LaunchSelectionDialog dialog = new LaunchSelectionDialog( shell );
    LaunchConfigLabelProvider result = new LaunchConfigLabelProvider( shell.getDisplay(), dialog, labelMode );
    labelProviders.add( result );
    return result;
  }

}
