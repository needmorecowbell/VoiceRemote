/*
 * 
 *voice remote Adam Musciano 4/5/2014
 * 
 * 
 * 
 *Sphinx
 Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

package demo.sphinx.voiceRemote;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

import com.sun.speech.freetts.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class voiceRemote {
	
    /**
     * Main method for running voiceRemote.
     * @throws URISyntaxException 
     * @throws AWTException 
     */
    public static void main(String[] args) throws URISyntaxException, AWTException {
    	final String SPEECHNAME= "kevin16";
    	
    	System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    	
        
        VoiceManager voiceManager = VoiceManager.getInstance();  
        Voice voice = voiceManager.getVoice(SPEECHNAME);  
       
        voice.allocate();
    	final String []CALL={ "charles","charlie"};
        try {
            URL url;
            if (args.length > 0) {
                url = new File(args[0]).toURI().toURL();
            } else {
                url = voiceRemote.class.getResource("voiceRemote.config.xml");
            }

            System.out.println("Loading...");
            ConfigurationManager config = new ConfigurationManager(url);
            
            Robot key= new Robot();
            Recognizer rec = (Recognizer) config.lookup("recognizer");
            Microphone mic = (Microphone) config.lookup("microphone");
            /* allocate the resource necessary for the recognizer */
            rec.allocate();

            /* the microphone will keep recording until the program exits */
            if (mic.startRecording()) {

            	while (true) {
            		System.out.println("Start speaking. Press Ctrl-C to quit.\n");

            		Result result = rec.recognize();
		    
            		if (result != null) {
            			String resultText = result.getBestFinalResultNoFiller();
            			
            			if(resultText.indexOf(CALL[0])!=-1 || resultText.indexOf(CALL[1])!=-1){
            				
            				System.out.println("You said: " + resultText);
            				
            				resultText=resultText.replaceAll(CALL[0]+" ", "");
            				resultText=resultText.replaceAll(CALL[1]+" ", "");
            				resultText=resultText.replaceAll(CALL[0], "");
            				resultText=resultText.replaceAll(CALL[1], "");
            				resultText=resultText.replaceAll("this ", "");
            				resultText=resultText.replaceAll("the", "");
            				
                			switch (resultText){
                				case("i solemnly swear i'm up to no good"):
                					voice.speak("Ah Harry Potter Little One");
                					java.awt.Desktop.getDesktop().browse(new URI("www.mugglenet.com"));
                					break;
                				case("hello "):
                					voice.speak("Good Day Sir.");
                					break;
                				case("what's up"):
                					voice.speak("Not Much Man!");
                					break;
                				case("how are you"):
                					voice.speak("I'm your slave, how would you feel?");
                					break;
                				case("get time"):
                					
                					Calendar cal = Calendar.getInstance();
                		    		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                		    		String time= sdf.format(cal.getTime());
                		    		String hour= time.substring(0,time.indexOf(":"));
                		    		String minutes= time.substring(time.indexOf(":"));
                					voice.speak("it is "+ hour+" "+minutes);
                					System.out.println("it is "+ sdf.format(cal.getTime()));
                					break;
                				case ("like song"):
                					voice.speak("Liking Song... ");
                					System.out.println("Liking Song... ");
                					key.keyPress(KeyEvent.VK_SHIFT);
                					key.keyPress(KeyEvent.VK_EQUALS);
                					key.keyRelease(KeyEvent.VK_EQUALS);
                					key.keyRelease(KeyEvent.VK_SHIFT);
                					break;
                				case ("dislike song"):
                					voice.speak("Disliking Song... ");
                					System.out.println("Disliking Song... ");
                					key.keyPress(KeyEvent.VK_SHIFT);
                					key.keyPress(KeyEvent.VK_MINUS);
                					key.keyRelease(KeyEvent.VK_EQUALS);
                					key.keyRelease(KeyEvent.VK_SHIFT);
                					break;
                				case ("pause song"):
                					voice.speak("Pausing/Resuming Song ");
                					System.out.println("Pausing/Resuming Song... ");
                					key.keyPress(KeyEvent.VK_SPACE);
                					key.keyRelease(KeyEvent.VK_SPACE);
                					break;
                				case ("next song"):
                					voice.speak("Next Song ");
                					System.out.println("Next Song... ");
                					key.keyPress(KeyEvent.VK_RIGHT);
                					key.keyRelease(KeyEvent.VK_RIGHT);
                					break;
                				case ("play song"):
                					voice.speak("Pausing or Resuming Song ");
                					System.out.println("Pausing/Resuming Song... ");
                					key.keyPress(KeyEvent.VK_SPACE);
                					key.keyRelease(KeyEvent.VK_SPACE);
                					break;
                				case ("close"):
                					voice.speak("Goodbye Sir");
                					System.out.println("Goodbye Sir... ");
                					voice.deallocate();
                					System.exit(1);
                					break;	
                				case ("open pandora"):
                					voice.speak("Opening Pandora");
                					System.out.println("Opening Pandora...");
                					java.awt.Desktop.getDesktop().browse(new URI("www.pandora.com"));
                					break;
                				case ("close pandora"):
                					voice.speak("closing Pandora ");
                					System.out.println("Closing Pandora...");
                					Runtime.getRuntime().exec("taskkill /IM chrome.exe");
                					break;
                				case("shut down"):
                					voice.speak("Shutting down in ten seconds");
                					System.out.println("Shutting down in 10 seconds");
    	    						Runtime.getRuntime().exec("shutdown -s -t 10");
    	    						break;
                				case("abort shut down"):
                					voice.speak("Aborted Shutdown");
                					System.out.println("Aborted Shutdown");
    	    						Runtime.getRuntime().exec("shutdown -a");
    	    						break;
                			} 
            			}

            		} else {
            			System.out.println("I can't hear what you said.\n");
            		}	
		    
            	}
            }else {
            	System.out.println("Cannot start microphone.");
            	rec.deallocate();
            	System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Problem when loading voiceRemote: " + e);
            e.printStackTrace();
        } catch (PropertyException e) {
            System.err.println("Problem configuring voiceRemote: " + e);
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.err.println("Problem creating voiceRemote: " + e);
            e.printStackTrace();
        }
        voice.deallocate();
    }
}
