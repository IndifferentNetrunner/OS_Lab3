/* It is in this file, specifically the replacePage function that will
   be called by MemoryManagement when there is a page fault.  The 
   users of this program should rewrite PageFault to implement the 
   page replacement algorithm.
*/

  // This PageFault file is an example of the FIFO Page Replacement 
  // Algorithm as described in the Memory Management section.

import java.util.*;

public class PageFault {
  /**
   * @param mem is the vector which contains the contents of the pages 
   *   in memory being simulated.  mem should be searched to find the 
   *   proper page to remove, and modified to reflect any changes.  
   * @param virtPageNum is the number of virtual pages in the 
   *   simulator (set in Kernel.java).  
   * @param replacePageNum is the requested page which caused the 
   *   page fault.  
   * @param controlPanel represents the graphical element of the 
   *   simulator, and allows one to modify the current display.
   */
  public static int counter = -1;

  public static void replacePage(Vector mem, int virtPageNum, int replacePageNum, ControlPanel controlPanel) {
    int pageToEject = -1;
    int min = -1;
    int count = 0;

    while (count != virtPageNum) {
      Page page = (Page) mem.elementAt(count);
      if (page.physical != -1) {
        if (min == -1 && page.counter != -1) {
          min = page.counter;
          pageToEject = count;
        } else {
          if (page.counter != -1 && page.counter < min) {
            pageToEject = count;
          } else {
            if (pageToEject == -1) {
              pageToEject = count;
            }
          }
        }
      }
      ++count;
    }

    // System.out.println(pageToEject + " " + min +  " " + count);

    Page page = (Page) mem.elementAt(pageToEject);
    Page nextpage = (Page) mem.elementAt(replacePageNum);
    controlPanel.removePhysicalPage(pageToEject);
    nextpage.physical = page.physical;
    controlPanel.addPhysicalPage(nextpage.physical, replacePageNum);
    page.inMemTime = 0;
    page.lastTouchTime = 0;
    page.R = 0;
    page.M = 0;
    page.physical = -1;
  }
}