package com.obs.redditclient.testing;

import com.keiferstone.nonet.ConnectionStatus;
import com.obs.redditclient.parsers.ItemParser;
import com.obs.redditclient.utils.Key;

import junit.framework.TestCase;

/**
 * Created by pedro on 20/3/17.
 */

public class TestHttp extends TestCase {

    @ConnectionStatus
    public void testHttp(){
        assertNotNull(Key.url);
        assertNotNull(Key.accion_listing);

        assertTrue(ItemParser.process());
    }

}
