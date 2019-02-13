/*
 * Copyright (c) 2019 M4taiori
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.m4taiori.discord.dischook.util;

import io.m4taiori.discord.dischook.events.WebhookEvent;
import io.m4taiori.discord.dischook.events.WebhookListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class EventManager
{

    private final List<WebhookListener> listeners = new ArrayList<>();

    public void registerListener(WebhookListener listener)
    {
        listeners.add(listener);
    }

    public void unregisterListener(WebhookListener listener)
    {
        listeners.remove(listener);
    }

    public void post(WebhookEvent event)
    {
        listeners.forEach
        (
            listener ->
            {
                for ( Method method : listener.getClass().getMethods() )
                {
                    if ( method.getParameterCount() == 1 )
                    {
                        Parameter parameter = method.getParameters()[0];
                        if ( WebhookEvent.class.isAssignableFrom( parameter.getType() ) && parameter.getType().equals(event.getClass()) )
                        {
                            try
                            {
                                method.invoke(listener, parameter.getType().cast(event) );
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        );
    }

}
