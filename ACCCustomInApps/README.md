<p align="center" >
    <img src=".github/accengage.png?raw=true" alt="Accengage logo" title="Accengage logo"> 
</p>

![Platform](https://img.shields.io/badge/platform-Android-lightgrey.svg?style=flat)
![Target](https://img.shields.io/badge/target-API%2015+-blue.svg?style=flat)

This demo demostrates how to customize Accengage in-app messages. Six different in-app templates are presented in the project:

<br/>
<p align="center" >
  <img src=".github/PopupBasic.png?raw=true" alt="Screenshot 1" title="Popup Basic">
  &nbsp;
  <img src=".github/PopupOffer.png?raw=true" alt="Screenshot 2" title="Popup Offer"> 
  &nbsp;
  <img src=".github/PopupPromo.png?raw=true" alt="Screenshot 3" title="Popup Promo"> 
  
</p>
<br/>

# Usage

To ensure a good and a smooth usage of this sample, there are a few important steps that you need to take.

1. App Configuration

  Complete the **AndroidManifest.xml** with your own `partner id` and `private key`.
  
2. Add a new In-App template
  
  In your application target you need to add a new In-App template. From the Settings panel press the :heavy_plus_sign: in the right of the **INAPP TEMPLATES**. When prompted, complete the fields with the next values:
  
  |                 |                                                     |
  |-----------------|-----------------------------------------------------|
  | Name            | Customizable Text Interstitial                      |
  | Value           | CustomizableTextInterstitial                        |
  | Type            | :white_large_square: Banner :white_check_mark: Text |
  | Message display | :white_check_mark:                                  |
  | Landing page    | :white_large_square:                                |
  | Interstitial    | :white_check_mark:                                  |
  |                 |                                                     |

  <p align="center" ><img src=".github/add_new_tempalte.gif?raw=true" alt="Accengage logo" title="Accengage logo"></p>
  
3. Create your segment
  
4. Create the In-App Notification

# Customising the appearance

In order to customise the In-App appearance, you can play with the attributes of each customizable object.

## Customizable objects

| Object          |       Id     |    Type     |
|-----------------|--------------|-------------|
| Title           | inapp_title  |   TextView  |
| Bold Text       | inapp_text1  |   TextView  |
| Description     | inapp_text2  |   TextView  |
| Confirmation    | inapp_cta    |   Button    |
| Image           | inapp_img    |   ImageView |
