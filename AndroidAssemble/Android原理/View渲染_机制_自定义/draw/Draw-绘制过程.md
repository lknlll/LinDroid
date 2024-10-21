[TOC]

## draw-绘制

ViewRootImpl#performTraversals 中

    performDraw()
        //fullRedrawNeeded，它的作用是判断是否需要重新绘制全部视图
        draw(fullRedrawNeeded);
        

    //ViewRootIml
    private void draw(boolean fullRedrawNeeded) {
        //...
        //获取mDirty，该值表示需要重绘的区域
        final Rect dirty = mDirty;
        if (mSurfaceHolder != null) {
          // The app owns the surface, we won't draw.
          dirty.setEmpty();
          if (animating) {
           if (mScroller != null) {
            mScroller.abortAnimation();
           }
           disposeResizeBuffer();
          }
          return;
         }
        
        //如果fullRedrawNeeded为真，则把dirty区域置为整个屏幕，表示整个视图都需要绘制
        //第一次绘制流程，需要绘制所有视图
        if (fullRedrawNeeded) {
          mAttachInfo.mIgnoreDirtyState = true;
          dirty.set(0, 0, (int) (mWidth * appScale + 0.5f), (int) (mHeight * appScale + 0.5f));
        }
        // ...
        if (!drawSoftware(surface, mAttachInfo, xOffset, yOffset, scalingRequired, dirty)) {
            return;
        }
    }
    
ViewRootImpl#drawSoftware


    private boolean drawSoftware(Surface surface, AttachInfo attachInfo, 
        int xoff, int yoff, boolean scalingRequired, Rect dirty) {
        final Canvas canvas;
        //锁定canvas区域，由dirty区域决定
        //这个canvas就是我们想在上面绘制东西的画布
        canvas = mSurface.lockCanvas(dirty);
        //...
        //画布支持位图的密度，和手机分辨率相关
        canvas.setDensity(mDensity);
        //...
        if (!canvas.isOpaque() || yoff != 0 || xoff != 0) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }
        //...
        canvas.translate(-xoff, -yoff);
        // ...
        //正式开始绘制
        mView.draw(canvas);
        //...
        //提交需要绘制的东西
        surface.unlockCanvasAndPost(canvas);
    }
    
mView.draw(canvas)即为调用了DecorView的draw()

    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (mMenuBackground != null) {
            mMenuBackground.draw(canvas);
        }
    }

这里开始走了View.draw()

### View.draw()



    public void draw(Canvas canvas) {
        final int privateFlags = mPrivateFlags;
        mPrivateFlags = (privateFlags & ~PFLAG_DIRTY_MASK) | PFLAG_DRAWN;

        /*
         * Draw traversal performs several drawing steps which must be executed
         * in the appropriate order:
         *
         *      1. Draw the background
         *      2. If necessary, save the canvas' layers to prepare for fading
         *      3. Draw view's content
         *      4. Draw children
         *      5. If necessary, draw the fading edges and restore layers
         *      6. Draw decorations (scrollbars for instance)
         */

        // Step 1, draw the background绘制背景, if needed
        int saveCount;

        drawBackground(canvas);

        // skip step 2 & 5 if possible (common case)
        // 如果可以就跳过2和5步（一般情况）
        final int viewFlags = mViewFlags;
        
        //判断是否有绘制衰退边缘的标示
        boolean horizontalEdges = (viewFlags & FADING_EDGE_HORIZONTAL) != 0;
        boolean verticalEdges = (viewFlags & FADING_EDGE_VERTICAL) != 0;
        
        //都没有，只需要346步
        if (!verticalEdges && !horizontalEdges) {
            // Step 3, draw the content
            onDraw(canvas);

            // Step 4, draw the children
            dispatchDraw(canvas);

            drawAutofilledHighlight(canvas);

            // Overlay is part of the content and draws beneath Foreground
            if (mOverlay != null && !mOverlay.isEmpty()) {
                mOverlay.getOverlayView().dispatchDraw(canvas);
            }

            // Step 6, draw decorations (foreground, scrollbars)
            onDrawForeground(canvas);

            // Step 7, draw the default focus highlight
            drawDefaultFocusHighlight(canvas);

            if (debugDraw()) {
                debugDrawFocus(canvas);
            }

            // we're done...
            return;
        }

        /*
         * Here we do the full fledged routine...
         * (this is an uncommon case where speed matters less,
         * this is why we repeat some of the tests that have been
         * done above)
         */

        boolean drawTop = false;
        boolean drawBottom = false;
        boolean drawLeft = false;
        boolean drawRight = false;

        float topFadeStrength = 0.0f;
        float bottomFadeStrength = 0.0f;
        float leftFadeStrength = 0.0f;
        float rightFadeStrength = 0.0f;

        // Step 2, save the canvas' layers
        int paddingLeft = mPaddingLeft;

        final boolean offsetRequired = isPaddingOffsetRequired();
        if (offsetRequired) {
            paddingLeft += getLeftPaddingOffset();
        }

        int left = mScrollX + paddingLeft;
        int right = left + mRight - mLeft - mPaddingRight - paddingLeft;
        int top = mScrollY + getFadeTop(offsetRequired);
        int bottom = top + getFadeHeight(offsetRequired);

        if (offsetRequired) {
            right += getRightPaddingOffset();
            bottom += getBottomPaddingOffset();
        }

        final ScrollabilityCache scrollabilityCache = mScrollCache;
        final float fadeHeight = scrollabilityCache.fadingEdgeLength;
        int length = (int) fadeHeight;

        // clip the fade length if top and bottom fades overlap
        // overlapping fades produce odd-looking artifacts
        if (verticalEdges && (top + length > bottom - length)) {
            length = (bottom - top) / 2;
        }

        // also clip horizontal fades if necessary
        if (horizontalEdges && (left + length > right - length)) {
            length = (right - left) / 2;
        }

        if (verticalEdges) {
            topFadeStrength = Math.max(0.0f, Math.min(1.0f, getTopFadingEdgeStrength()));
            drawTop = topFadeStrength * fadeHeight > 1.0f;
            bottomFadeStrength = Math.max(0.0f, Math.min(1.0f, getBottomFadingEdgeStrength()));
            drawBottom = bottomFadeStrength * fadeHeight > 1.0f;
        }

        if (horizontalEdges) {
            leftFadeStrength = Math.max(0.0f, Math.min(1.0f, getLeftFadingEdgeStrength()));
            drawLeft = leftFadeStrength * fadeHeight > 1.0f;
            rightFadeStrength = Math.max(0.0f, Math.min(1.0f, getRightFadingEdgeStrength()));
            drawRight = rightFadeStrength * fadeHeight > 1.0f;
        }

        saveCount = canvas.getSaveCount();
        int topSaveCount = -1;
        int bottomSaveCount = -1;
        int leftSaveCount = -1;
        int rightSaveCount = -1;

        int solidColor = getSolidColor();
        if (solidColor == 0) {
            if (drawTop) {
                topSaveCount = canvas.saveUnclippedLayer(left, top, right, top + length);
            }

            if (drawBottom) {
                bottomSaveCount = canvas.saveUnclippedLayer(left, bottom - length, right, bottom);
            }

            if (drawLeft) {
                leftSaveCount = canvas.saveUnclippedLayer(left, top, left + length, bottom);
            }

            if (drawRight) {
                rightSaveCount = canvas.saveUnclippedLayer(right - length, top, right, bottom);
            }
        } else {
            scrollabilityCache.setFadeColor(solidColor);
        }

        // Step 3, draw the content
        onDraw(canvas);

        // Step 4, draw the children
        dispatchDraw(canvas);

        // Step 5, draw the fade effect and restore layers
        final Paint p = scrollabilityCache.paint;
        final Matrix matrix = scrollabilityCache.matrix;
        final Shader fade = scrollabilityCache.shader;

        // must be restored in the reverse order that they were saved
        if (drawRight) {
            matrix.setScale(1, fadeHeight * rightFadeStrength);
            matrix.postRotate(90);
            matrix.postTranslate(right, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor == 0) {
                canvas.restoreUnclippedLayer(rightSaveCount, p);

            } else {
                canvas.drawRect(right - length, top, right, bottom, p);
            }
        }

        if (drawLeft) {
            matrix.setScale(1, fadeHeight * leftFadeStrength);
            matrix.postRotate(-90);
            matrix.postTranslate(left, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor == 0) {
                canvas.restoreUnclippedLayer(leftSaveCount, p);
            } else {
                canvas.drawRect(left, top, left + length, bottom, p);
            }
        }

        if (drawBottom) {
            matrix.setScale(1, fadeHeight * bottomFadeStrength);
            matrix.postRotate(180);
            matrix.postTranslate(left, bottom);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor == 0) {
                canvas.restoreUnclippedLayer(bottomSaveCount, p);
            } else {
                canvas.drawRect(left, bottom - length, right, bottom, p);
            }
        }

        if (drawTop) {
            matrix.setScale(1, fadeHeight * topFadeStrength);
            matrix.postTranslate(left, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor == 0) {
                canvas.restoreUnclippedLayer(topSaveCount, p);
            } else {
                canvas.drawRect(left, top, right, top + length, p);
            }
        }

        canvas.restoreToCount(saveCount);

        drawAutofilledHighlight(canvas);

        // Overlay is part of the content and draws beneath Foreground
        if (mOverlay != null && !mOverlay.isEmpty()) {
            mOverlay.getOverlayView().dispatchDraw(canvas);
        }

        // Step 6, draw decorations (foreground, scrollbars)
        onDrawForeground(canvas);

        if (debugDraw()) {
            debugDrawFocus(canvas);
        }
    }


绘制流程的六个步骤：

1. 对 View 的背景进行绘制

2. 保存当前的图层信息(可跳过)

3. 绘制 View 的内容

4. 对 View 的子 View 进行绘制(如果有子 View )

5. 绘制 View 的褪色的边缘，类似于阴影效果(可跳过)

6. 绘制 View 的装饰（例如：滚动条）


#### 第一步 绘制背景



    //View
    private void drawBackground(Canvas canvas) {
       //获取背景的Drawable，没有就不需要绘制
        final Drawable background = mBackground;
        if (background == null) {
            return;
        }
       //确定背景Drawable边界
        setBackgroundBounds();
        ...

       //如果有偏移量先偏移画布再将drawable绘制上去
        final int scrollX = mScrollX;
        final int scrollY = mScrollY;
        if ((scrollX | scrollY) == 0) {
            background.draw(canvas);
        } else {
            canvas.translate(scrollX, scrollY);
            //此处会执行各种Drawable对应的draw方法
            background.draw(canvas);
            //把画布的原点移回去，drawable在屏幕上的位置不动
            canvas.translate(-scrollX, -scrollY);
        }
    }
    
    
#### 第三步 绘制 View 的内容

这里先跳过第 2 步，是因为不是所有的 View 都需绘制褪色边缘

这一步就是走DecorView自身onDraw()绘制自己，这里DecorView只是绘制一些背景颜色，


    mBackgroundFallback.draw(this, mContentRoot, c, mWindow.mContentParent,
                mStatusColorViewState.view, mNavigationColorViewState.view);
                

View的onDraw()默认是空实现；

#### 第四步 绘制子View

DecorView 绘制完成后，开始绘制子 View，细节通过下面方法

    //ViewGroup#dispatchDraw
    protected void dispatchDraw(Canvas canvas) {
       boolean usingRenderNodeProperties = canvas.isRecordingFor(mRenderNode);
       final int childrenCount = mChildrenCount;
       final View[] children = mChildren;
       int flags = mGroupFlags;

        //ViewGroup是否有设置子View入场动画，如果有绑定到View
        // 启动动画控制器
        //...
        
        //指定修改区域
       int clipSaveCount = 0;
       final boolean clipToPadding = (flags & CLIP_TO_PADDING_MASK) == CLIP_TO_PADDING_MASK;
       // 不让子view绘制在pandding里面，也就是去除padding
       if (clipToPadding) {
           clipSaveCount = canvas.save();
           canvas.clipRect(mScrollX + mPaddingLeft, mScrollY + mPaddingTop,
                   mScrollX + mRight - mLeft - mPaddingRight,
                   mScrollY + mBottom - mTop - mPaddingBottom);
       }

        //...

       for (int i = 0; i < childrenCount; i++) {
       
        //先取mTransientViews中的View，mTransientViews中的View
        //通过addTransientView添加，它们只是容器渲染的一个item
        
           while (transientIndex >= 0 && mTransientIndices.get(transientIndex) == i) {
               final View transientChild = mTransientViews.get(transientIndex);
               if ((transientChild.mViewFlags & VISIBILITY_MASK) == VISIBLE ||
                       transientChild.getAnimation() != null) {
                   more |= drawChild(canvas, transientChild, drawingTime);
               }
               transientIndex++;
               if (transientIndex >= transientCount) {
                   transientIndex = -1;
               }
           }
           int childIndex = customOrder ? getChildDrawingOrder(childrenCount, i) : i;
           final View child = (preorderedList == null)
                   ? children[childIndex] : preorderedList.get(childIndex);
           if ((child.mViewFlags & VISIBILITY_MASK) == VISIBLE || child.getAnimation() != null) {
               more |= drawChild(canvas, child, drawingTime);
           }
       }
        //...
    }
    
ViewGroup#dispatchDraw 的流程

先启动第一次加到布局中的动画，然后确定绘制区域，遍历绘制 View，遍历 View 的时候优先绘制渲染的 mTransientViews，绘制 View 调用到ViewGroup#drawChild：

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        //View.draw有两个重载方法
        //这个多参数的draw用于view绘制自身内容
        return child.draw(canvas, this, drawingTime);
    }
    


    boolean draw(Canvas canvas, ViewGroup parent, long drawingTime) {
        
        boolean drawingWithRenderNode = mAttachInfo != null
                && mAttachInfo.mHardwareAccelerated
                && hardwareAcceleratedCanvas;
        //...
        
        //主要判断是否有绘制缓存，
        //如果有，直接使用缓存，如果没有，调用 draw(canvas)方法
        if (!drawingWithDrawingCache) {
            if (drawingWithRenderNode) {
                mPrivateFlags &= ~PFLAG_DIRTY_MASK;
                ((DisplayListCanvas) canvas).drawRenderNode(renderNode);
            } else {
                // Fast path for layouts with no backgrounds
                if ((mPrivateFlags & PFLAG_SKIP_DRAW) == PFLAG_SKIP_DRAW) {
                    mPrivateFlags &= ~PFLAG_DIRTY_MASK;
                    dispatchDraw(canvas);
                } else {
                    draw(canvas);
                }
            }
        } else if (cache != null) {
            mPrivateFlags &= ~PFLAG_DIRTY_MASK;
            if (layerType == LAYER_TYPE_NONE) {
                // no layer paint, use temporary paint to draw bitmap
                Paint cachePaint = parent.mCachePaint;
                if (cachePaint == null) {
                    cachePaint = new Paint();
                    cachePaint.setDither(false);
                    parent.mCachePaint = cachePaint;
                }
                cachePaint.setAlpha((int) (alpha * 255));
                canvas.drawBitmap(cache, 0.0f, 0.0f, cachePaint);
            } else {
                // use layer paint to draw the bitmap, merging the two alphas, but also restore
                int layerPaintAlpha = mLayerPaint.getAlpha();
                mLayerPaint.setAlpha((int) (alpha * layerPaintAlpha));
                canvas.drawBitmap(cache, 0.0f, 0.0f, mLayerPaint);
                mLayerPaint.setAlpha(layerPaintAlpha);
            }
        }
    }
    
    
首先判断是否已经有缓存，即之前是否已经绘制过一次了，如果没有，则会调用 draw(canvas) 方法，开始正常的绘制，即上面所说的六个步骤，这里就是View逐层draw的体现；

#### 第六步 绘制装饰

除了背景、内容、子 View 的其余部分，例如滚动条

    public void onDrawForeground(Canvas canvas) {
        //绘制滑动指示
        onDrawScrollIndicators(canvas);
        //绘制ScrollBar
        onDrawScrollBars(canvas);
        //获取前景色的Drawable，绘制到canvas上
        final Drawable foreground = mForegroundInfo != null ? mForegroundInfo.mDrawable : null;
        if (foreground != null) {
            if (mForegroundInfo.mBoundsChanged) {
                mForegroundInfo.mBoundsChanged = false;
                final Rect selfBounds = mForegroundInfo.mSelfBounds;
                final Rect overlayBounds = mForegroundInfo.mOverlayBounds;
                if (mForegroundInfo.mInsidePadding) {
                    selfBounds.set(0, 0, getWidth(), getHeight());
                } else {
                    selfBounds.set(getPaddingLeft(), getPaddingTop(),
                            getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
                }
                final int ld = getLayoutDirection();
                Gravity.apply(mForegroundInfo.mGravity, foreground.getIntrinsicWidth(),
                        foreground.getIntrinsicHeight(), selfBounds, overlayBounds, ld);
                foreground.setBounds(overlayBounds);
            }
            foreground.draw(canvas);
        }
    }
    
#### 第二步 第五步 绘制褪色边缘

当 horizontalEdges 或者 verticalEdges 有一个 true 的时候，表示需要绘制 View 的褪色边缘：这时候先计算出是否需要绘制上下左右的褪色边缘和它的参数，然后保存视图层：


### invalidate

//TODO
https://blog.csdn.net/a553181867/article/details/51583060

view 的 invalidate 不会导致 ViewRootImpl 的 invalidate 被调用，而是递归调用父 view的invalidateChildInParent，直到 ViewRootImpl 的 invalidateChildInParent，然后触发peformTraversals，会导致当前 view 被重绘,由于 mLayoutRequested 为 false，不会导致 onMeasure 和 onLayout 被调用，而 onDraw 会被调用

一个 view 的 invalidate 会导致本身 `PFLAG_INVALIDATED` 置 1，导致本身以及父族 viewgroup 的 `PFLAG_DRAWING_CACHE_VALID` 置 0

requestLayout 会直接递归调用父窗口的 requestLayout，直到 ViewRootImpl，然后触发 peformTraversals，由于 mLayoutRequested 为 true，会导致 onMeasure 和onLayout 被调用。不一定会触发 onDraw

requestLayout 触发 onDraw 可能是因为在在 layout 过程中发现 l, t, r, b 和以前不一样，那就会触发一次 invalidate，所以触发了onDraw，也可能是因为别的原因导致 mDirty 非空（比如在跑动画）

requestLayout 会导致自己以及父族 view 的 `PFLAG_FORCE_LAYOUT` 和 `PFLAG_INVALIDATED` 标志被设置。

一般来说，只要刷新的时候就调用 invalidate，需要重新 measure 就调用 requestLayout，后面再跟个 invalidate（为了保证重绘），


//Todo ViewGroup的draw和onDraw的调用时机
https://juejin.cn/post/6844903502276198407

//Todo View dirtyOpaque android-29 changes