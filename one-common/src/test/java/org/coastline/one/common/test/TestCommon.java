package org.coastline.one.common.test;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Jay.H.Zou
 * @date 2023/3/9
 */
public class TestCommon {

    @Test
    public void testMock() {
        List mockedList = mock(List.class);
        //using mock object
        mockedList.add("one");
        mockedList.clear();
        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }
}
