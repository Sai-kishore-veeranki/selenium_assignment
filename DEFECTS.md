# Magnus Functional Defects

These are reproducible issues observed in the Magnus training pages and are formatted for JIRA logging. The link examples are on the application's dedicated broken-links training tab, so confirm whether their behavior is intentional before assigning them as product defects.

## MAG-1: Broken Links examples resolve to the Magnus site instead of their displayed host

**Summary:** The three links on the Broken Links tab omit the URL scheme and resolve as relative Magnus URLs.

**Steps to Reproduce:**
1. Sign in to Magnus and open More > Links.
2. Select the Broken Links tab.
3. Click any of the three Link 1, Link 2, or Link 3 entries.

**Expected Result:** Each example should navigate to its stated external broken-link test destination.

**Actual Result:** Each anchor has `href="www.brokenlinkcheck.com/"`, so the browser resolves it under the Magnus origin rather than to `www.brokenlinkcheck.com`.

**Severity:** Low

## MAG-2: Broken Links tab sends all three examples to the same destination

**Summary:** Link 1, Link 2, and Link 3 on the Broken Links tab have identical destinations.

**Steps to Reproduce:**
1. Sign in to Magnus and open More > Links.
2. Select the Broken Links tab.
3. Inspect the destination of each displayed link.

**Expected Result:** The examples should provide distinct link targets so each broken-link scenario can be tested independently.

**Actual Result:** All three links use the same `www.brokenlinkcheck.com/` href.

**Severity:** Low

## MAG-3: Frame One uses an insecure HTTP iframe source

**Summary:** The iFrames page embeds SeleniumHQ using an HTTP URL inside the HTTPS Magnus site.

**Steps to Reproduce:**
1. Sign in to Magnus and open More > iFrames.
2. Inspect or switch into Frame One.

**Expected Result:** The embedded content should use HTTPS and load securely without relying on browser mixed-content handling.

**Actual Result:** The frame source is `http://www.seleniumhq.org/`; it only reaches its HTTPS destination through a redirect.

**Severity:** Low

## MAG-4: In-window popup close controls do not dismiss the modal

**Summary:** The in-window popup remains open after either close control is clicked.

**Steps to Reproduce:**
1. Sign in to Magnus and open More > Popups.
2. Click In Window Popup.
3. Click the modal's X or Close button.

**Expected Result:** The modal should close and return focus to the Popup page.

**Actual Result:** The modal remains visible over the page after the close control is clicked.

**Severity:** Low

## MAG-5: Slider value label does not reflect the selected slider value

**Summary:** The slider handle moves, but the displayed current-value text remains unchanged.

**Steps to Reproduce:**
1. Sign in to Magnus and open More > Slider.
2. Move the slider handle away from its initial value.

**Expected Result:** The current slider value text should update to match the handle position.

**Actual Result:** The handle tooltip changes to the selected value, but the text below the slider remains at `3`.

**Severity:** Low
